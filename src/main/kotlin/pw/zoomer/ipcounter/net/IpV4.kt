package pw.zoomer.ipcounter.net

data class IpV4(val octet1: Int, val octet2: Int, val octet3: Int, val octet4: Int) {
    init {
        if (octet1 !in 0..255 || octet2 !in 0..255 || octet3 !in 0..255 || octet4 !in 0..255) {
            throw IllegalArgumentException("Illegal IP address $octet1.$octet2.$octet3.$octet4")
        }
    }

    companion object {
        fun fromString(ipAddress: String): IpV4 {
            val octets = ipAddress.split('.').map { parseOctet(it) }
            if (octets.size != 4) throw IllegalArgumentException("Illegal IP address $ipAddress")
            return IpV4(octets[0], octets[1], octets[2], octets[3])
        }

        private fun parseOctet(octet: String): Int {
            val value = octet.toIntOrNull()
            if (value == null || value !in 0..255) {
                throw IllegalArgumentException("Illegal IP address octet $octet")
            }
            return value
        }
    }
}