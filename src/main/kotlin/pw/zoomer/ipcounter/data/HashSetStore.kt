package pw.zoomer.ipcounter.data

import pw.zoomer.ipcounter.net.IpV4

class HashSetStore : IpStore {
    private val highIPs = HashSet<Long>()
    private val lowIPs = HashSet<Long>()

    override val count: Long
        get() = highIPs.size.toLong() + lowIPs.size

    override fun add(ipAddress: IpV4) {
        if (ipAddress.octet1 and 0b1000_0000 == 0) {
            lowIPs.add(ipAddress.toLong())
        } else {
            highIPs.add(ipAddress.toLong())
        }
    }
}