package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.composite

import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.Type
import jp.co.soramitsu.substrate_sdk.scale.dataType.CollectionEnumScaleType

class CollectionEnum(
    name: String,
    val elements: List<String>
) : Type<String>(name) {

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): String {
        return CollectionEnumScaleType(elements).read(scaleCodecReader)
    }

    override fun encode(scaleCodecWriter: ScaleCodecWriter, runtime: RuntimeSnapshot, value: String) {
        CollectionEnumScaleType(elements).write(scaleCodecWriter, value)
    }

    override fun isValidInstance(instance: Any?): Boolean {
        return instance in elements
    }

    operator fun get(key: Int): String = elements[key]

    override val isFullyResolved = true
}
