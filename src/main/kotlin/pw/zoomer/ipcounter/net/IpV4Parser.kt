package pw.zoomer.ipcounter.net

import java.net.Inet4Address
import java.nio.ByteBuffer

/**
 * IP V4 Parser based on java.net.Inet4Address.
 * @see java.net.Inet4Address
 */
sealed class IpV4Parser {
    companion object {
        /**
         * Parses IP address or host name to Long IP number or return null if address is not parsable or hostname
         * cannot be resolved.
         * @see Inet4Address.getByName
         */
        fun toLongOrNull(ip: String): Long? {
            if (ip.isEmpty()) {
                return null
            }
            return try {
                Inet4Address.getByName(ip).let {
                    val signedIp = ByteBuffer.wrap(it.address).int.toLong()
                    if (signedIp >= 0) signedIp else ((signedIp and 0xFF_FF_FF_FF) or (1L shl 31))
                }
            } catch (ex: java.net.UnknownHostException) {
                null
            }
        }
    }
}