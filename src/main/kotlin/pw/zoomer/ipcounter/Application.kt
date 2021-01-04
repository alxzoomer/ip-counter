package pw.zoomer.ipcounter

import pw.zoomer.ipcounter.data.IpAsBitsArrayStore
import pw.zoomer.ipcounter.data.IpAsMappedBitSetStore
import pw.zoomer.ipcounter.io.FileTextReader
import pw.zoomer.ipcounter.jobs.IpCounter
import pw.zoomer.ipcounter.log.ConsoleLogger
import pw.zoomer.ipcounter.log.Logger
import pw.zoomer.ipcounter.performance.Stats
import java.io.File

/**
 * Application is bootstrap class.
 */
class Application(private val args: Array<String>) {
    private val logger: Logger = ConsoleLogger()
    private val stats = Stats(logger)

    /**
     * Parse arguments and start application if arguments is correct or show help if incorrect.
     */
    fun start() {
        if (args.size == 1 && File(args[0]).isFile) {
            stats.measureTimeMillis { stats.memStats { countIps(File(args[0])) } }
        } else {
            help()
        }
    }

    private fun help() {
        println("Run application: java -jar ip-counter.jar <IP list file path>")
    }

    private fun countIps(file: File) {
        logger.info("Start IP counting")
        // Fastest solution but memory expensive on app start.
        val ipStore = IpAsBitsArrayStore()
        // IpAsMappedBitSetStore slower for about ~25% than IpAsBitsArrayStore
        // but can use memory more effective if IP list includes only few sub-networks
        // val ipStore = IpAsMappedBitSetStore()
        val counter = IpCounter(FileTextReader(file.absolutePath), ipStore, logger)
        counter.start()
        logger.info("Unique IP count: ${counter.count}")
    }
}