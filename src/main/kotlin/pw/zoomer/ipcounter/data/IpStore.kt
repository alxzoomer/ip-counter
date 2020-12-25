package pw.zoomer.ipcounter.data

import pw.zoomer.ipcounter.net.IpV4

interface IpStore {
    val count: Long

    fun add(ipAddress: IpV4)
}