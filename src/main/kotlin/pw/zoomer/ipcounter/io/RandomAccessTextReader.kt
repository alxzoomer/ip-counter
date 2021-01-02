package pw.zoomer.ipcounter.io

import java.io.FileInputStream
import java.io.RandomAccessFile

/**
 * Read file from specified position.
 * @param filePath Full path to file to read.
 * @param startPosition Start position in file to read.
 * @param endPosition End position in file to read.
 * @param skipIncompleteLine When true and startPosition != 0 the first line from position will be skipped.
 */
class RandomAccessTextReader(
    private val filePath: String,
    private val startPosition: Long,
    private val endPosition: Long,
    private val skipIncompleteLine: Boolean = false,
    private val useRoughPositionTracker: Boolean = true
) : TextReader {

    private interface FilePositionTracker {
        fun canRead(line: String?): Boolean
    }

    private class SimplePositionTracker(private val file: RandomAccessFile, private val endPosition: Long) :
        FilePositionTracker {
        override fun canRead(line: String?) = line?.let { file.filePointer <= endPosition } ?: false
    }

    private class PositionTracker(private val file: RandomAccessFile, private val endPosition: Long) :
        FilePositionTracker {
        // Rough position calculated as line length of single chars + \n
        private var roughPosition = 0L

        // Adjustment position used to minimize position error
        private var adjustmentPosition = 0L

        init {
            adjustRoughPosition()
        }

        private fun adjustRoughPosition() {
            roughPosition = file.filePointer
            adjustmentPosition = roughPosition + DEFAULT_BUFFER_SIZE
        }

        override fun canRead(line: String?): Boolean {
            return line?.let {
                roughPosition += it.length + 1
                if (roughPosition > adjustmentPosition) {
                    adjustRoughPosition()
                }
                // filePointer is expensive for CPU
                !(roughPosition > endPosition && file.filePointer > endPosition)
            } ?: false
        }
    }

    override fun forEachLine(action: (line: String) -> Unit) {
        RandomAccessFile(filePath, "r").use { file ->
            val reader = FileInputStream(file.fd).bufferedReader()
            file.seek(startPosition)
            // Skip the first line when start position is not equal to 0 because it can be incomplete.
            if (skipIncompleteLine && startPosition > 0 && reader.readLine() == null) return

            val tracker = if (useRoughPositionTracker) {
                PositionTracker(file, endPosition)
            } else {
                SimplePositionTracker(file, endPosition)
            }
            while (tracker.canRead(reader.readLine()?.also(action)));
        }
    }

    companion object {
        /**
         * Default buffer size for BufferedReader class.
         */
        const val DEFAULT_BUFFER_SIZE = 8192
    }
}