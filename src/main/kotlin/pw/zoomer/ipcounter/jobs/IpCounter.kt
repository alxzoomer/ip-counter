package pw.zoomer.ipcounter.jobs

import pw.zoomer.ipcounter.data.IpStore
import pw.zoomer.ipcounter.io.TextReader
import pw.zoomer.ipcounter.log.Logger
import pw.zoomer.ipcounter.net.QuickIpV4Parser

/**
 * Class reads IP addresses from text reader source line by line and writes parsed IP's to IP store.
 * @param reader TextReader instance as IP source.
 * @param ipStore IpStore destination IP store.
 * @param logger ILogger logger instance to write parsing errors.
 */
class IpCounter(
    private val reader: TextReader,
    private val ipStore: IpStore,
    private val logger: Logger
) {
    /**
     * Get count of stored IPs.
     */
    val count
        get() = ipStore.count

    /**
     * Start IP reading and parsing.
     */
    fun start() {
        reader.forEachLine {
            if (QuickIpV4Parser.toLongOrNull(it)?.let { ip -> ipStore.add(ip) } == null) {
                logger.error("Incorrect IP address $it")
            }
        }
    }
}