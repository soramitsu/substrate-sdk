package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale

import com.ionspin.kotlin.bignum.integer.BigInteger

enum class CompactMode(val value: Byte) {

    SINGLE(0.toByte()),
    TWO(1.toByte()),
    FOUR(2.toByte()),
    BIGINT(3.toByte());

    companion object {
        private val MAX = BigInteger.TWO.pow(536).subtract(BigInteger.ONE)
        fun byValue(value: Byte): CompactMode {
            return when (value) {
                SINGLE.value -> SINGLE
                TWO.value -> TWO
                FOUR.value -> FOUR
                else -> BIGINT
            }
        }

        fun forNumber(number: Int): CompactMode {
            return forNumber(number.toLong())
        }

        fun forNumber(number: Long): CompactMode {
            require(number >= 0) { "Negative numbers are not supported" }
            return if (number <= 0x3f) {
                SINGLE
            } else if (number <= 0x3fff) {
                TWO
            } else if (number <= 0x3fffffff) {
                FOUR
            } else {
                BIGINT
            }
        }

        fun forNumber(number: BigInteger): CompactMode {
            require(number.signum() >= 0) { "Negative numbers are not supported" }
            require(number <= MAX) { "Numbers larger than 2**536-1 are not supported" }
            return if (number == BigInteger.ZERO) {
                SINGLE
            } else if (number > BigInteger.fromLong(0x3fffffff)) {
                BIGINT
            } else if (number > BigInteger.fromLong(0x3fff)) {
                FOUR
            } else if (number > BigInteger.fromLong(0x3f)) {
                TWO
            } else {
                SINGLE
            }
        }
    }
}