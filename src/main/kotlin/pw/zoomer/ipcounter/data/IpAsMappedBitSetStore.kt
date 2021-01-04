package pw.zoomer.ipcounter.data

import java.util.BitSet

/**
 * Stores unique IP addresses as map of BitSet class instances.
 * Major IP address octet is map key to bit sets for the rest octets.
 */
class IpAsMappedBitSetStore : IpStore {
    private val bitsStore = mutableMapOf<Int, BitSet>()

    override val count: Long
        get() = bitsStore.values.sumOf { it.cardinality().toLong() }

    override fun add(ipAddress: Long) {
        val firstOctet = (ipAddress shr 24).toInt()
        val bitIndex = (ipAddress and 0xFF_FF_FF).toInt()
        if (bitsStore[firstOctet]?.set(bitIndex) == null) {
            BitSet(0x01_00_00_00).also {
                bitsStore[firstOctet] = it
                it.set(bitIndex)
            }
        }
    }
}