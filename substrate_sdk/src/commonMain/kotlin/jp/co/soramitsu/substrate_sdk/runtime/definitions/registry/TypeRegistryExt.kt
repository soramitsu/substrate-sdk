package jp.co.soramitsu.substrate_sdk.runtime.definitions.registry

import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.Type

fun TypeRegistry.getOrThrow(
    definition: String
): Type<*> {
    return get(definition) ?: error("Type $definition was not found.")
}
