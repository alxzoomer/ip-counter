package pw.zoomer.ipcounter.io

import java.io.File

/**
 * Read text file from beginning till the end line by line.
 * @param filePath Full path to text file.
 */
class FileTextReader(filePath: String): TextReader {
    private var file: File = File(filePath)

    /**
     * Read text file and call action.
     * @param action Call action for each line.
     */
    override fun forEachLine(action: (line: String) -> Unit) {
        file.forEachLine(action = action)
    }
}