package jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate

import jp.co.soramitsu.substrate_sdk.encrypt.keypair.derivePublicKeyEcdsa

actual class ECDSASubstrateKeypairFactory : BaseECDSASubstrateKeypairFactory() {

    actual override fun derivePublicKey(privateKeyOrSeed: ByteArray): ByteArray {
        return derivePublicKeyEcdsa(privateKeyOrSeed)
    }
}