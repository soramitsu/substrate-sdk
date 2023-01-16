package jp.co.soramitsu.substrate_sdk.scale.dataType.utils

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.CompactMode
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.CompactULongWriter

private val LONG_WRITER = CompactULongWriter()

internal class CompactBigIntWriter : ScaleWriter<BigInteger> {

    @Throws(IOException::class)
    override fun write(scaleWriter: ScaleCodecWriter, value: BigInteger) {
        val mode = CompactMode.forNumber(value)
        val data = value.toUnsignedBytes()
        var pos = data.size - 1
        if (mode != CompactMode.BIGINT) {
            LONG_WRITER.write(scaleWriter, value.longValue())
        } else {
            scaleWriter.directWrite((data.size - 4 shl 2) + mode.value)
            while (pos >= 0) {
                scaleWriter.directWrite(data[pos].toInt())
                --pos
            }
        }
    }
}