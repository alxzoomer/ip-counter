package pw.zoomer.ipcounter.performance

import pw.zoomer.ipcounter.log.ILogger

class Stats(private val logger: ILogger) {
    fun memStat() {
        val r = Runtime.getRuntime()
        val free = r.freeMemory() / 1024 / 1024
        val total = r.totalMemory() / 1024 / 1024
        logger.info("Free mem Mb: $free, Total mem Mb: $total, Uses mem Mb: ${total - free}")
    }

    fun memStats(block: () -> Unit) {
        memStat()
        try {
            block()
        } finally {
            memStat()
        }
    }

    fun measureTimeMillis(block: () -> Unit) {
        val start = System.currentTimeMillis()
        try {
            block()
        } finally {
            val total = System.currentTimeMillis() - start
            logger.info("Total time ms: $total")
        }
    }
}
