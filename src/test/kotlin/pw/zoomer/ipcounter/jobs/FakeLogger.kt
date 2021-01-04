package pw.zoomer.ipcounter.jobs

import pw.zoomer.ipcounter.log.Logger

class FakeLogger : Logger {
    private var errorsLogged = 0

    val errors
        get() = errorsLogged

    override fun error(message: String) {
        errorsLogged++
    }

    override fun info(message: String) {
    }
}