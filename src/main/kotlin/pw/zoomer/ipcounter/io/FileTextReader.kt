package pw.zoomer.ipcounter.io

import java.io.File

/**
 * Read text file from beginning till the end line by line.
 * @param pathName Full path to text file.
 */
class FileTextReader(pathName: String): TextReader {
    private var file: File = File(pathName)

    /**
     * Read text file and call action.
     * @param action Call action for each line.
     */
    override fun forEachLine(action: (line: String) -> Unit) {
        file.forEachLine(action = action)
    }
}