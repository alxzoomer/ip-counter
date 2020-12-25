package pw.zoomer.ipcounter.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pw.zoomer.ipcounter.net.IpV4

class HashSetStoreTest {
    @Test
    fun testCountAfterConstruction() {
        val store = HashSetStore()
        assertEquals(0, store.count)
    }

    @Test
    fun testCountInEdgeCase() {
        val store = HashSetStore()
        listOf("0.0.0.0", "127.255.255.255", "128.255.255.255", "255.255.255.255").forEach {
            store.add(IpV4.fromString(it))
        }
        assertEquals(4, store.count)
    }

    @Test
    fun testDuplicatedIPsCounting() {
        val store = HashSetStore()
        listOf(
            "0.0.0.0",
            "0.0.0.0",
            "1.1.1.1",
            "1.1.1.1",
            "128.128.128.128",
            "128.128.128.128",
            "255.255.255.255",
            "255.255.255.255"
        ).forEach {
            store.add(IpV4.fromString(it))
        }
        assertEquals(4, store.count)
    }
}