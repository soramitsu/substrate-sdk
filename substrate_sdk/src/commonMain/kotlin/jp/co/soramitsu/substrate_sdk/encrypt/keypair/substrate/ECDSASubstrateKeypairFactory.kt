package jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate

import jp.co.soramitsu.substrate_sdk.encrypt.keypair.derivePublicKeyEcdsa

class ECDSASubstrateKeypairFactory : OtherSubstrateKeypairFactory() {

    override val hardDerivationPrefix: String = "Secp256k1HDKD"

    override fun deriveFromSeed(seed: ByteArray): KeypairWithSeed {
        return KeypairWithSeed(
            seed = seed,
            privateKey = seed,
            publicKey = derivePublicKey(seed)
        )
    }

    private fun derivePublicKey(privateKeyOrSeed: ByteArray): ByteArray {
        return derivePublicKeyEcdsa(privateKeyOrSeed)
    }
}