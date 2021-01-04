package pw.zoomer.ipcounter.jobs

import pw.zoomer.ipcounter.io.TextReader

class FakeTextReader(private val ips: List<String>) : TextReader {
    override fun forEachLine(action: (line: String) -> Unit) = ips.forEach(action)
}