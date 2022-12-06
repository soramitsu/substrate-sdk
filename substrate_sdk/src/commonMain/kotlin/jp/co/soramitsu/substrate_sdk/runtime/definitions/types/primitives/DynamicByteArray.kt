package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.primitives

import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.dataType.byteArrayScale

class DynamicByteArray(name: String) : Primitive<ByteArray>(name) {

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): ByteArray {
        return byteArrayScale.read(scaleCodecReader)
    }

    override fun encode(scaleCodecWriter: ScaleCodecWriter, runtime: RuntimeSnapshot, value: ByteArray) {
        return byteArrayScale.write(scaleCodecWriter, value)
    }

    override fun isValidInstance(instance: Any?): Boolean {
        return instance is ByteArray
    }
}
