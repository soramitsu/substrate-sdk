package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.composite

import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.Type
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.TypeReference
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.skipAliasesOrNull

class Tuple(name: String, val typeReferences: List<TypeReference>) : Type<List<*>>(name) {

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): List<*> {
        return typeReferences.map { it.requireValue().decode(scaleCodecReader, runtime) }
    }

    override fun encode(scaleCodecWriter: ScaleCodecWriter, runtime: RuntimeSnapshot, value: List<*>) {
        typeReferences.zip(value).onEach { (type, value) ->
            type.requireValue().encodeUnsafe(scaleCodecWriter, runtime, value)
        }
    }

    override fun isValidInstance(instance: Any?): Boolean {
        if (instance !is List<*>) return false

        val zipped = typeReferences.zip(instance)

        return zipped.size == typeReferences.size && zipped.all { (type, possibleValue) ->
            type.requireValue().isValidInstance(possibleValue)
        }
    }

    operator fun get(index: Int): Type<*>? = typeReferences[index].skipAliasesOrNull()?.value

    override val isFullyResolved: Boolean
        get() = typeReferences.all { it.isResolved() }
}
