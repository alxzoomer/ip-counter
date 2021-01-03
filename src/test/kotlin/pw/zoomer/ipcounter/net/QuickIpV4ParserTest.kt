package pw.zoomer.ipcounter.net

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertNull

class QuickIpV4ParserTest {
    @ParameterizedTest
    @CsvSource(
        "0.0.0.0, 0",
        "127.127.127.127, 2139062143",
        "255.255.255.255, 4294967295",
        "255.128.16.1, 4286582785"
    )
    fun verifyToLong(ipAddress: String, ipAddressLong: Long) {
        val ip = QuickIpV4Parser.toLongOrNull(ipAddress)
        assertEquals(ipAddressLong, ip)
    }

    @ParameterizedTest
    @CsvSource(
        "''",
        "256.0.0.0",
        "0.256.0.0",
        "0.0.256.0",
        "0.0.0.256",
        "9223372036854775807.0.0.0",
        "-9223372036854775809.0.0.0"
    )
    fun verifyInvalidIpFromStringReturnsNull(ipAddress: String) {
        val ip = QuickIpV4Parser.toLongOrNull(ipAddress)
        assertNull(ip)
    }
}