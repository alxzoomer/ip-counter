package pw.zoomer.ipcounter.net

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class IpV4Test {
    @ParameterizedTest
    @CsvSource(
        "0,0,0,0",
        "1,1,1,1",
        "254,254,254,254",
        "255,255,255,255"
    )
    fun verifyDefaultConstructor(octet1: Int, octet2: Int, octet3: Int, octet4: Int) {
        val ip = IpV4(octet1, octet2, octet3, octet4)
        assertEquals(octet1, ip.octet1)
        assertEquals(octet2, ip.octet2)
        assertEquals(octet3, ip.octet3)
        assertEquals(octet4, ip.octet4)
    }

    @ParameterizedTest
    @CsvSource(
        "0.0.0.0, 0,0,0,0",
        "1.1.1.1, 1,1,1,1",
        "254.254.254.254, 254,254,254,254",
        "255.255.255.255, 255,255,255,255"
    )
    fun verifyFromString(ipAddress: String, octet1: Int, octet2: Int, octet3: Int, octet4: Int) {
        val ip = IpV4.fromString(ipAddress)
        assertEquals(octet1, ip.octet1)
        assertEquals(octet2, ip.octet2)
        assertEquals(octet3, ip.octet3)
        assertEquals(octet4, ip.octet4)
    }

    @ParameterizedTest
    @CsvSource(
        "0.0.0.0, 0",
        "127.127.127.127, 2139062143",
        "255.255.255.255, 4294967295"
    )
    fun verifyToUInt(ipAddress: String, ipAddressUInt: Long) {
        val ip = IpV4.fromString(ipAddress)
        assertEquals(ipAddressUInt.toUInt(), ip.toUInt())
    }

    @ParameterizedTest
    @CsvSource(
        "-1,0,0,0",
        "0,-1,0,0",
        "0,0,-1,0",
        "0,0,0,-1"
    )
    fun verifyInvalidIpViaConstructorThrowsException(octet1: Int, octet2: Int, octet3: Int, octet4: Int) {
        assertThrows<IllegalArgumentException> {
            IpV4(octet1, octet2, octet3, octet4)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "''",
        "256.0.0.0",
        "0.256.0.0",
        "0.0.256.0",
        "0.0.0.256"
    )
    fun verifyInvalidIpFromStringThrowsException(ipAddress: String) {
        assertThrows<IllegalArgumentException> {
            IpV4.fromString(ipAddress)
        }
    }
}