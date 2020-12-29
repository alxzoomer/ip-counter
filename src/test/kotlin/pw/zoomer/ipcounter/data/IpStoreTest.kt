package pw.zoomer.ipcounter.data

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pw.zoomer.ipcounter.net.IpV4Parser

interface IpStoreTest<T> where T: IpStore {
    fun createInstance() : T

    @Test
    fun testCountAfterConstruction() {
        val store = createInstance()
        Assertions.assertEquals(0, store.count)
    }

    @Test
    fun testCountInEdgeCase() {
        val store = createInstance()
        listOf("0.0.0.0", "127.255.255.255", "128.255.255.255", "255.255.255.255").forEach {
            store.add(IpV4Parser.toLongOrNull(it) ?: -1)
        }
        Assertions.assertEquals(4, store.count)
    }

    @Test
    fun testDuplicatedIPsCounting() {
        val store = createInstance()
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
            IpV4Parser.toLongOrNull(it)?.let { ip -> store.add(ip) }
        }
        Assertions.assertEquals(4, store.count)
    }
}