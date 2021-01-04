package pw.zoomer.ipcounter.log

/**
 * Simple logger interface.
 */
interface Logger {
    /**
     * Log error message.
     * @param message Error message to log.
     */
    fun error(message: String)

    /**
     * Log information message.
     * @param message Information message to log.
     */
    fun info(message: String)
}