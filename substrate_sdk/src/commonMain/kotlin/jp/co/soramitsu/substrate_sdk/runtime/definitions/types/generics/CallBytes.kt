package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.generics

import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.primitives.Primitive
import jp.co.soramitsu.substrate_sdk.scale.dataType.byteArraySizedScale

object CallBytes : Primitive<String>("CallBytes") {

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): String {
        throw NotImplementedError() // the same as in polkascan implementation
    }

    override fun encode(scaleCodecWriter: ScaleCodecWriter, runtime: RuntimeSnapshot, value: String) {
        val bytes = value.fromHex()

        byteArraySizedScale(bytes.size).write(scaleCodecWriter, bytes)
    }

    override fun isValidInstance(instance: Any?): Boolean {
        return instance is String
    }
}
