package pw.zoomer.ipcounter.data

/**
 * Store IP addresses inside two hash sets.
 * For 100M IP addresses required about 7Gb of heap space.
 */
class HashSetStore : IpStore {
    private val highIPs = HashSet<Long>()
    private val lowIPs = HashSet<Long>()

    override val count: Long
        get() = highIPs.size.toLong() + lowIPs.size

    override fun add(ipAddress: Long) {
        if (ipAddress < 0) {
            lowIPs.add(ipAddress)
        } else {
            highIPs.add(ipAddress)
        }
    }
}