package pw.zoomer.ipcounter.net

/**
 * Quick IP V4 parser works only with IP V4 in format x.x.x.x and faster than IpV4Parser in 10+ times.
 */
sealed class QuickIpV4Parser {
    private enum class ParsingState {
        WHITESPACE_OR_DIGIT,
        DIGIT_OR_DOT,
        DIGIT
    }

    companion object {
        /**
         * Parse and validate IP V4 address.
         * @return Long value if address is valid otherwise return null.
         */
        fun toLongOrNull(str: String): Long? {
            if (str.isEmpty()) return null
            var acc = 0
            var ip = 0L
            var state = ParsingState.WHITESPACE_OR_DIGIT
            var octetNum = 1
            for (ch in str) when (ch) {
                in '0'..'9' -> {
                    acc = (acc * 10) + (ch - '0')
                    if (acc !in 0..255) return null
                    state = if (octetNum == 4) ParsingState.WHITESPACE_OR_DIGIT else ParsingState.DIGIT_OR_DOT
                }
                '.' -> {
                    if (++octetNum > 4) return null
                    ip = (ip shl 8) + acc
                    acc = 0
                    state = ParsingState.DIGIT
                }
                '\t', ' ' -> {
                    if (state == ParsingState.WHITESPACE_OR_DIGIT && octetNum == 4) {
                        break
                    }
                    return null
                }
                else -> return null
            }
            return if (state == ParsingState.WHITESPACE_OR_DIGIT) (ip shl 8) + acc else null
        }
    }
}