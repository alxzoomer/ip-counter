package pw.zoomer.ipcounter.jobs

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IpCounterTest {
    @Test
    fun countCorrectIps() {
        val ips = listOf("0.0.0.0", "1.1.1.1", "1.1.1.1", "127.127.127.127", "255.255.255.255")
        val counter = IpCounter(FakeTextReader(ips), FakeIpStore(), FakeLogger())
        counter.start()
        Assertions.assertEquals(4, counter.count)
    }

    @Test
    fun countErrorsOfIncorrectIps() {
        val ips = listOf("256.0.0.0", "1.1.1.", "1.1.1", "1.1.", "1.1", "1.", "1")
        val logger = FakeLogger()
        val counter = IpCounter(FakeTextReader(ips), FakeIpStore(), logger)
        counter.start()
        Assertions.assertEquals(7, logger.errors)
    }
}