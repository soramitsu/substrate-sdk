package jp.co.soramitsu.substrate_sdk.scale.codecWriters

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter

actual class BoolWriter: ScaleWriter<Boolean> {

    override fun write(scaleWriter: ScaleCodecWriter, value: Boolean) {
        TODO()
    }
}