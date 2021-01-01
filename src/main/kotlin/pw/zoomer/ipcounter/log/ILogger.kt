package pw.zoomer.ipcounter.log

interface ILogger {
    fun error(message: String)
    fun info(message: String)
}