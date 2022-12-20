package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.primitives

import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

open class FixedByteArray(name: String, val length: Int) : Primitive<ByteArray>(name) {

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): ByteArray {
        return scaleCodecReader.readByteArray(length)
    }

    override fun encode(scaleCodecWriter: ScaleCodecWriter, runtime: RuntimeSnapshot, value: ByteArray) {
        return scaleCodecWriter.directWrite(value, 0, length)
    }

    override fun isValidInstance(instance: Any?): Boolean {
        return instance is ByteArray && instance.size == length
    }
}
