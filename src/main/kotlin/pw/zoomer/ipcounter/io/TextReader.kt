package pw.zoomer.ipcounter.io

/**
 * Interface provides access to text source line by line.
 */
interface TextReader {
    /**
     * Reads text source line by line and calls action for each line.
     * @param action Executing action on each line.
     */
    fun forEachLine(action: (line: String) -> Unit)
}