package pw.zoomer.ipcounter.data

/**
 * Stores unique IP addresses as map of arrays.
 * Major IP address octet is map key to array of bits for the rest octets.
 */
class IpAsBitsMappedArraysStore : IpStore {
    private val bitsStore = mutableMapOf<Int, LongArray>()
    private var counter = 0L

    override val count: Long
        get() = counter

    override fun add(ipAddress: Long) {
        val firstOctet = (ipAddress shr 24).toInt()
        val restOctets = ipAddress and 0xFF_FF_FF
        val register = (restOctets / 64).toInt()
        val mask = 1L shl (restOctets % 64).toInt()
        var lastOctetsArray = bitsStore[firstOctet]
        if (lastOctetsArray == null) {
            lastOctetsArray = LongArray(0x04_00_00)
            bitsStore[firstOctet] = lastOctetsArray
        }
        val value = lastOctetsArray[register]
        if (value and mask == 0L) {
            lastOctetsArray[register] = value or mask
            counter++
        }
    }
}