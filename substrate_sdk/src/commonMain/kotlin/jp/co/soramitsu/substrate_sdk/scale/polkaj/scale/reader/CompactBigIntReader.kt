package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.CompactMode

class CompactBigIntReader : ScaleReader<BigInteger> {

    override fun read(reader: ScaleCodecReader): BigInteger {
        val type = reader.readUByte()
        val mode = CompactMode.byValue((type and 3).toByte())
        if (mode != CompactMode.BIGINT) {
            reader.skip(-1)
            val value = intReader.read(reader)
            return BigInteger.fromLong(value.toLong())
        }
        val len = (type shr 2) + 4
        val value = reader.readByteArray(len)
        //LE encoded, so need to reverse it
        for (i in 0 until value.size / 2) {
            val temp = value[i]
            value[i] = value[value.size - i - 1]
            value[value.size - i - 1] = temp
        }
        //unsigned, i.e. always positive, signum=1
        return BigInteger.fromByteArray(value, Sign.POSITIVE)
    }

    companion object {
        private val intReader = CompactUIntReader()
    }
}