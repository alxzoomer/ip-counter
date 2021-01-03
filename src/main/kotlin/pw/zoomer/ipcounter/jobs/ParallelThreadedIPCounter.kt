package pw.zoomer.ipcounter.jobs

import pw.zoomer.ipcounter.data.IpStore
import pw.zoomer.ipcounter.io.TextReader
import pw.zoomer.ipcounter.log.ILogger
import pw.zoomer.ipcounter.net.QuickIpV4Parser
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

/**
 * Class reads IP addresses in parallel from text reader source line by line and writes parsed IP's to non-blocking
 * queue.
 * Queue reads from separate thread and each IP writes to the IP store.
 * @param readerInitBlock TextReader init block.
 * @param store destination IP store.
 * @param logger ILogger logger instance to write parsing errors.
 */
class ParallelThreadedIPCounter(
    private val readerInitBlock: (jobNumber: Int, jobsCount: Int) -> TextReader,
    private val store: IpStore,
    private val logger: ILogger
) {
    private val queue = ConcurrentLinkedQueue<Long>()
    private val stopQueueJob = AtomicBoolean(false)

    /**
     * Get count of stored IPs.
     */
    val count: Long
        get() = store.count

    /**
     * Start IP reading and parsing.
     * @param jobsCount Count of parallel jobs.
     */
    fun start(jobsCount: Int) {
        if (jobsCount < 1) throw java.lang.IllegalArgumentException("jobsCount must have positive value")
        logger.info("Run $jobsCount threads")

        val queueJob = thread(start = true, name = "Queue job") { processQueue() }
        val jobs = (0 until jobsCount).map {
            val reader = readerInitBlock(it, jobsCount)
            thread(start = true, name = "Job ${it + 1} of $jobsCount") { countLines(reader) }
        }
        jobs.forEach { it.join() }
        stopQueueJob.set(true)
        queueJob.join()
    }

    private fun countLines(reader: TextReader) {
        logger.info("${Thread.currentThread().name} started")
        var i = 0L
        reader.forEachLine { line ->
            if (QuickIpV4Parser.toLongOrNull(line)?.also { ip -> queue.add(ip) } == null) {
                logger.error("Incorrect IP address $line")
            }
            i++
            if (i % 10_000_00 == 0L) {
                logger.info("${Thread.currentThread().name}: processed $i")
            }
        }
        logger.info("${Thread.currentThread().name} work done")
    }

    private fun processQueue() {
        logger.info("Start queue processing")
        while (!stopQueueJob.get() || queue.isNotEmpty()) {
            if (queue.poll()?.also { store.add(it) } == null) {
                // Allow CPU core make another useful work when queue is empty
                Thread.sleep(1)
            }
        }
        logger.info("Stop queue processing")
    }
}