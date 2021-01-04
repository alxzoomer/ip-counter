package pw.zoomer.ipcounter.jobs

import pw.zoomer.ipcounter.data.IpStore

class FakeIpStore: IpStore {
    private val ips = HashSet<Long>()
    override val count: Long
        get() = ips.size.toLong()

    override fun add(ipAddress: Long) {
        ips.add(ipAddress)
    }
}