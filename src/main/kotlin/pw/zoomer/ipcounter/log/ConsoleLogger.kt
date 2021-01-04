package pw.zoomer.ipcounter.log

/**
 * Write logs to standard output.
 */
class ConsoleLogger : Logger {
    override fun error(message: String) {
        println("ERROR: $message")
    }

    override fun info(message: String) {
        println("INFO: $message")
    }

}