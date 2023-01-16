package jp.co.soramitsu.substrate_sdk.scale.polkaj.types

open class ByteData(value: ByteArray?) {

    protected val value: ByteArray

    init {
        if (value == null) {
            throw NullPointerException("Value is null")
        }
        this.value = value.copyOf()
    }//make a copy to ensure immutability

    /**
     * @return bytes value
     */
    val bytes: ByteArray
        get() =//make a copy to ensure immutability
            value.copyOf()

    override fun toString(): String {
        val hex = CharArray(value.size * 2 + 2)
        hex[0] = '0'
        hex[1] = 'x'
        for (i in value.indices) {
            val b = value[i]
            hex[2 + i * 2] = (b.toInt() and 0xf0 shr 4).digitToChar(radix = 16)
            hex[2 + i * 2 + 1] = (b.toInt() and 0x0f).digitToChar(radix = 16)
        }
        return hex.concatToString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is ByteData) return false
        return value.contentEquals(o.value)
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }

    companion object {
        fun from(hex: String?): ByteData {
            val value = FixedBytes.parseHex(hex)
            return ByteData(value)
        }

        fun empty(): ByteData {
            return ByteData(ByteArray(0))
        }
    }
}