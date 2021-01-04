package pw.zoomer.ipcounter.log

interface Logger {
    fun error(message: String)
    fun info(message: String)
}