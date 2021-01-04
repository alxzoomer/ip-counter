package pw.zoomer.ipcounter.data

/**
 * Interface describes storage of unique IP V4 addresses and provides count property.
 */
interface IpStore {
    /**
     * Count of unique IP V4 addresses.
     */
    val count: Long

    /**
     * Add IP V4 address to storage if it does not exists in storage.
     * @param ipAddress long number IP address representation.
     */
    fun add(ipAddress: Long)
}