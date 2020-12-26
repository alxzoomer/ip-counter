package pw.zoomer.ipcounter.io

interface TextReader {
    fun forEachLine(action: (line: String) -> Unit)
}