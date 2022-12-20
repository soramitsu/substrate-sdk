package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.primitives

import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.Type

abstract class Primitive<I>(name: String) : Type<I>(name) {

    override val isFullyResolved = true
}
