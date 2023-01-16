package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

class BoolOptionalWriter : ScaleWriter<Boolean?> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Boolean?) {
        if (value == null) {
            scaleCodecWriter.directWrite(0)
        } else if (value) {
            scaleCodecWriter.directWrite(2)
        } else {
            scaleCodecWriter.directWrite(1)
        }
    }
}