package pw.zoomer.ipcounter.data

/**
 * Stores each unique IP address as separate bit inside long array collection.
 * The class instance will take permanently about 500Mb of heap space when created.
 */
class IpAsBitsArrayStore : IpStore {
    private val bitsStore = LongArray(0x04_00_00_00)

    override val count: Long
        get() = bitsStore.sumOf { it.countOneBits().toLong() }

    override fun add(ipAddress: Long) {
        val register = (ipAddress / 64).toInt()
        val mask = 1L shl (ipAddress % 64).toInt()
        val value = bitsStore[register]
        if (value and mask == 0L) {
            bitsStore[register] = value or mask
        }
    }

}