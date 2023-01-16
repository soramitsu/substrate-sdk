package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException

class UInt32Writer : ScaleWriter<Int> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Int) {
        require(value >= 0) { "Negative values are not supported: $value" }
        scaleCodecWriter.directWrite(value and 0xff)
        scaleCodecWriter.directWrite(value shr 8 and 0xff)
        scaleCodecWriter.directWrite(value shr 16 and 0xff)
        scaleCodecWriter.directWrite(value shr 24 and 0xff)
    }
}