package jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate

abstract class BaseECDSASubstrateKeypairFactory : OtherSubstrateKeypairFactory() {

    override val hardDerivationPrefix: String = "Secp256k1HDKD"

    override fun deriveFromSeed(seed: ByteArray): KeypairWithSeed {
        return KeypairWithSeed(
            seed = seed,
            privateKey = seed,
            publicKey = derivePublicKey(seed)
        )
    }

    protected abstract fun derivePublicKey(privateKeyOrSeed: ByteArray): ByteArray
}

expect class ECDSASubstrateKeypairFactory() : BaseECDSASubstrateKeypairFactory {
    override fun derivePublicKey(privateKeyOrSeed: ByteArray): ByteArray
}
