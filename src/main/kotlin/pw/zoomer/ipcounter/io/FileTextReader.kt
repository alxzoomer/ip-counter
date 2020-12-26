package pw.zoomer.ipcounter.io

import java.io.File

class FileTextReader(val pathName: String): TextReader {
    private var file: File = File(pathName)

    override fun forEachLine(action: (line: String) -> Unit) {
        file.forEachLine(action = action)
    }
}