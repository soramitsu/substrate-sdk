package jp.co.soramitsu.substrate_sdk.runtime

import jp.co.soramitsu.substrate_sdk.runtime.definitions.registry.TypeRegistry
import jp.co.soramitsu.substrate_sdk.runtime.metadata.RuntimeMetadata

typealias OverriddenConstantsMap = Map<String, Map<String, String>>

class RuntimeSnapshot(
    val typeRegistry: TypeRegistry,
    val metadata: RuntimeMetadata,
    val overrides: OverriddenConstantsMap? = null
)
