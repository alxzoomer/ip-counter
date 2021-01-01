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
    private val skipIncompleteLine: Boolean = false
) : TextReader {

    override fun forEachLine(action: (line: String) -> Unit) {
        RandomAccessFile(filePath, "r").use { file ->
            val reader = FileInputStream(file.fd).bufferedReader()
            file.seek(startPosition)
            // Skip the first line when start position is not equal to 0 because it can be incomplete.
            if (skipIncompleteLine && startPosition > 0 && reader.readLine() == null) return
            do {
                val line = reader.readLine()?.also(action)
                // File pointer is multiple of default buffer reader size i.e. to 8192 bytes and
                // not points to actual position after line length.
            } while (line != null && file.filePointer <= endPosition)
        }
    }

    companion object {
        /**
         * Default buffer size for BufferedReader class.
         */
        const val DEFAULT_BUFFER_SIZE = 8192
    }
}