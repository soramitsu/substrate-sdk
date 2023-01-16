package jp.co.soramitsu.substrate_sdk.scale.polkaj.types

abstract class FixedBytes protected constructor(
    value: ByteArray, expectedSize: Int
) : ByteData(value) {
    init {
        require(value.size == expectedSize) { "Value size must be " + expectedSize + "; received: " + value.size }
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }

    protected operator fun compareTo(o: FixedBytes): Int {
        check(value.size == o.value.size) { "Different size " + value.size + " != " + o.value.size }
        for (i in value.indices) {
            if (value.get(i) != o.value.get(i)) {
                return value.get(i) - o.value.get(i)
            }
        }
        return 0
    }

    companion object {
        fun parseHex(hex: String, expectedSize: Int): ByteArray {
            var hex = hex
            if (hex.length == expectedSize * 2 + 2 && hex.startsWith("0x")) {
                hex = hex.substring(2)
            }
            require(hex.length == expectedSize * 2) { "Invalid hex size: " + hex.length }
            return parseHex(hex)
        }

        fun parseHex(hex: String?): ByteArray {
            var hex = hex ?: throw NullPointerException("Hex value is null")
            if (hex.startsWith("0x")) {
                hex = hex.substring(2)
            }
            val len = hex.length
            if (len % 2 != 0) {
                throw NumberFormatException("Not event number of digits provided")
            }
            val data = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                data[i / 2] = hex.substring(i, i + 2).toInt(16).toByte()
                i += 2
            }
            return data
        }
    }
}