package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

class ULong32Writer : ScaleWriter<Long> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Long) {
        require(value >= 0) { "Negative values are not supported: $value" }
        require(value <= 0xffffffffL) { "Value is too high: $value" }
        scaleCodecWriter.directWrite((value and 0xffL).toInt())
        scaleCodecWriter.directWrite((value shr 8 and 0xffL).toInt())
        scaleCodecWriter.directWrite((value shr 16 and 0xffL).toInt())
        scaleCodecWriter.directWrite((value shr 24 and 0xffL).toInt())
    }
}