package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class UInt128Reader : ScaleReader<BigInteger> {

    override fun read(reader: ScaleCodecReader): BigInteger {
        val value = reader.readByteArray(SIZE_BYTES)
        reverse(value)
        return BigInteger.fromByteArray(value, Sign.POSITIVE)
    }

    companion object {
        const val SIZE_BYTES = 16
        fun reverse(value: ByteArray) {
            for (i in 0 until value.size / 2) {
                val other = value.size - i - 1
                val tmp = value[other]
                value[other] = value[i]
                value[i] = tmp
            }
        }
    }
}