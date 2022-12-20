package jp.co.soramitsu.substrate_sdk.runtime.definitions.registry.preprocessors

import jp.co.soramitsu.substrate_sdk.runtime.definitions.registry.RequestPreprocessor

object RemoveGenericNoisePreprocessor : RequestPreprocessor {

    private val REGEX = "(T::)|(<T>)|(<T as Trait>::)|(<T as Trait<I>>::)|(<T as Config>::)|(\n)|((grandpa|session|slashing|schedule)::)".toRegex()

    override fun process(definition: String) = definition.replace(REGEX, "")
}
