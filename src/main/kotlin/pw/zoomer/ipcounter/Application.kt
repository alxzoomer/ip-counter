package pw.zoomer.ipcounter

import pw.zoomer.ipcounter.data.IpAsBitsArrayStore
import pw.zoomer.ipcounter.io.RandomAccessTextReader
import pw.zoomer.ipcounter.io.TextReader
import pw.zoomer.ipcounter.jobs.ParallelThreadedIPCounter
import pw.zoomer.ipcounter.log.ConsoleLogger
import pw.zoomer.ipcounter.log.ILogger
import pw.zoomer.ipcounter.performance.Stats
import java.io.File
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.max

/**
 * Application is bootstrap class.
 */
class Application(private val args: Array<String>) {
    private val logger: ILogger = ConsoleLogger()
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
        val ipStore = IpAsBitsArrayStore()
        val counter = ParallelThreadedIPCounter(
            { jobNumber, jobsCount -> getTextReader(file, jobNumber, jobsCount) },
            { ipStore },
            logger
        )
        val jobsCount = calcJobsCount(file)
        counter.start(jobsCount)
        logger.info("Unique IP count: ${counter.count}")
    }

    /**
     * Creates text reader for specific file and calculate file start and end position to read.
     */
    private fun getTextReader(file: File, jobNumber: Int, jobsCount: Int): TextReader {
        val chunkSize = file.length() / jobsCount
        val chunkStartPosition = chunkSize * jobNumber
        val chunkEndPosition = if (jobNumber < jobsCount - 1) {
            chunkSize * (jobNumber + 1) + RandomAccessTextReader.DEFAULT_BUFFER_SIZE
        } else {
            file.length()
        }
        return RandomAccessTextReader(
            file.absolutePath,
            chunkStartPosition,
            chunkEndPosition,
            skipIncompleteLine = true
        )
    }

    /**
     * Calculates jobs count. When file size enough small and less then buffered reader buffer size then it
     * will be 1 job otherwise job count will be not more than CPU cores count and not more than file size
     * divided to default buffer size.
     */
    private fun calcJobsCount(file: File) = max(
        min(
            ceil(file.length() * 1f / RandomAccessTextReader.DEFAULT_BUFFER_SIZE).toInt(),
            Runtime.getRuntime().availableProcessors()
        ), 1
    )
}