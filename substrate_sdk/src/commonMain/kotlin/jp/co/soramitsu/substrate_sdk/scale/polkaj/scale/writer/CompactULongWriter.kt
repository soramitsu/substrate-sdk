package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.CompactMode
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

class CompactULongWriter : ScaleWriter<Long> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Long) {
        val mode = CompactMode.forNumber(value)
        var compact: Long
        var bytes: Int
        if (mode == CompactMode.BIGINT) {
            BIGINT_WRITER.write(scaleCodecWriter, BigInteger.fromLong(value))
            return
        } else {
            compact = (value shl 2) + mode.value
            bytes = if (mode == CompactMode.SINGLE) {
                1
            } else if (mode == CompactMode.TWO) {
                2
            } else {
                4
            }
        }
        while (bytes > 0) {
            scaleCodecWriter.directWrite(compact.toInt() and 0xff)
            compact = compact shr 8
            bytes--
        }
    }

    companion object {
        private val BIGINT_WRITER = CompactBigIntWriter()
    }
}