package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter

class UByteWriter : ScaleWriter<Int> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Int) {
        require(!(value < 0 || value > 0xff)) { "Only values in range 0..255 are supported: $value" }
        scaleCodecWriter.directWrite(value)
    }
}