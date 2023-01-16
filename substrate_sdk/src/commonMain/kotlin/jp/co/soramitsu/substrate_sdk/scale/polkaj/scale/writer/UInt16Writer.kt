package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException

class UInt16Writer : ScaleWriter<Int> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Int) {
        scaleCodecWriter.directWrite(value and 0xff)
        scaleCodecWriter.directWrite(value shr 8 and 0xff)
    }
}