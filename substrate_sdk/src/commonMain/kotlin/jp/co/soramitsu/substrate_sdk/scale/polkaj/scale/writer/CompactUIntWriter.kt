package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.CompactMode
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

class CompactUIntWriter : ScaleWriter<Int> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Int) {
        val mode = CompactMode.forNumber(value)
        var compact: Int
        var bytes: Int
        if (mode == CompactMode.BIGINT) {
            scaleCodecWriter.directWrite(mode.value.toInt())
            compact = value
            bytes = 4
        } else {
            compact = (value shl 2) + mode.value
            bytes = when (mode) {
                CompactMode.SINGLE -> 1
                CompactMode.TWO -> 2
                else -> 4
            }
        }
        while (bytes > 0) {
            scaleCodecWriter.directWrite(compact and 0xff)
            compact = compact shr 8
            bytes--
        }
    }
}