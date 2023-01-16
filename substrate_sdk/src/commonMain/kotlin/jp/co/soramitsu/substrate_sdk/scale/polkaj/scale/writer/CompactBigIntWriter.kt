package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.CompactMode
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

class CompactBigIntWriter : ScaleWriter<BigInteger> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: BigInteger) {
        val mode = CompactMode.forNumber(value)
        val data = value.toByteArray()
        var length = data.size
        var pos = data.size - 1
        var limit = 0
        if (mode != CompactMode.BIGINT) {
            LONG_WRITER.write(scaleCodecWriter, value.longValue())
            return
        }

        // skip the first byte if it's 0
        if (data[0].toInt() == 0x00) {
            length--
            limit++
        }
        scaleCodecWriter.directWrite((length - 4 shl 2) + mode.value)
        while (pos >= limit) {
            scaleCodecWriter.directWrite(data[pos].toInt())
            pos--
        }
    }

    companion object {
        private val LONG_WRITER = CompactULongWriter()
    }
}