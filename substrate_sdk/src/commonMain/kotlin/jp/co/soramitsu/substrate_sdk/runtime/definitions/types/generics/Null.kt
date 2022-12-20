package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.generics

import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.primitives.Primitive
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

object Null : Primitive<Any?>("Null") {

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): Any? {
        return null
    }

    override fun encode(scaleCodecWriter: ScaleCodecWriter, runtime: RuntimeSnapshot, value: Any?) {
        // pass
    }

    override fun isValidInstance(instance: Any?): Boolean {
        return instance == null
    }
}
