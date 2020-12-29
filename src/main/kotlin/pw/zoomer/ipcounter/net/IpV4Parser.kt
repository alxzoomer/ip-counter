package pw.zoomer.ipcounter.net

sealed class IpV4Parser {
    companion object {
        fun toLongOrNull(ip: String): Long? {
            val octets = ip.trim().split('.').map { it.toLongOrNull() ?: -1 }
            if (octets.size != 4 || octets.any { it !in 0..255L }) {
                return null
            }
            return (octets[0] shl 24) + (octets[1] shl 16) + (octets[2] shl 8) + octets[3]
        }
    }
}