package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.stub

import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.Type
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

class FakeType(name: String) : Type<Nothing>(name) {

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): Nothing {
        throw IllegalArgumentException("Fake")
    }

    override fun encode(scaleCodecWriter: ScaleCodecWriter, runtime: RuntimeSnapshot, value: Nothing) {
        throw IllegalArgumentException("Fake")
    }

    override fun isValidInstance(instance: Any?): Boolean {
        return false
    }

    override val isFullyResolved = true
}
