package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

class BoolWriter : ScaleWriter<Boolean> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Boolean) {
        if (value) {
            scaleCodecWriter.directWrite(1)
        } else {
            scaleCodecWriter.directWrite(0)
        }
    }
}