package jp.co.soramitsu.substrate_sdk.runtime.definitions.dynamic

import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.Type
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.TypeReference

typealias TypeProvider = (typeDef: String) -> TypeReference

interface DynamicTypeExtension {

    fun createType(name: String, typeDef: String, typeProvider: TypeProvider): Type<*>?
}
