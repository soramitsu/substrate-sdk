package jp.co.soramitsu.substrate_sdk.encrypt.keypair

import jp.co.soramitsu.substrate_sdk.encrypt.junction.Junction

internal interface KeypairFactory<K : Keypair> {

    class SoftDerivationNotSupported : Exception()

    fun deriveFromSeed(seed: ByteArray): K

    fun deriveChild(parent: K, junction: Junction): K
}
