package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.primitives

import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.ScaleCodecWriters

object BooleanType : Primitive<Boolean>("bool") {

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): Boolean {
        return scaleCodecReader.readBoolean()
    }

    override fun encode(scaleCodecWriter: ScaleCodecWriter, runtime: RuntimeSnapshot, value: Boolean) {
        scaleCodecWriter.write(ScaleCodecWriters.BOOL, value)
    }

    override fun isValidInstance(instance: Any?) = instance is Boolean
}
