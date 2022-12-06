package jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate

actual class ECDSASubstrateKeypairFactory : BaseECDSASubstrateKeypairFactory() {
    
    actual override fun derivePublicKey(privateKeyOrSeed: ByteArray): ByteArray {
        TODO()
    }
}