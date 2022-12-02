package jp.co.soramitsu.xnetworking.runtime.definitions.types.generics

import jp.co.soramitsu.xnetworking.runtime.definitions.registry.TypePresetBuilder
import jp.co.soramitsu.xnetworking.runtime.definitions.registry.getOrCreate
import jp.co.soramitsu.xnetworking.runtime.definitions.types.TypeReference
import jp.co.soramitsu.xnetworking.runtime.definitions.types.composite.Struct
import jp.co.soramitsu.xnetworking.runtime.definitions.types.composite.Vec

@Suppress("FunctionName")
fun EventRecord(typePresetBuilder: TypePresetBuilder) = Struct(
    name = "EventRecord",
    mapping = linkedMapOf(
        "phase" to typePresetBuilder.getOrCreate("Phase"),
        "event" to typePresetBuilder.getOrCreate("GenericEvent"),
        "topics" to TypeReference(
            Vec(
                name = "Vec<Hash>",
                typeReference = typePresetBuilder.getOrCreate("Hash")
            )
        )
    )
)
