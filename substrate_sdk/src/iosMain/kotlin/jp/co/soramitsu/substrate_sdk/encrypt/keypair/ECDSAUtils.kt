package jp.co.soramitsu.substrate_sdk.encrypt.keypair

import cocoapods.IrohaCrypto.SECKeyFactory
import cocoapods.IrohaCrypto.SECPrivateKey
import jp.co.soramitsu.substrate_sdk.common.toByteArray
import jp.co.soramitsu.substrate_sdk.common.toData

private val internalFactory = SECKeyFactory()

actual fun derivePublicKeyEcdsa(privateKeyOrSeed: ByteArray): ByteArray {
    val childPrivateKey = SECPrivateKey(rawData = privateKeyOrSeed.toData(), error = null)
    val publicKey = internalFactory.deriveFromPrivateKey(
        privateKey = childPrivateKey,
        error = null
    )!!.publicKey()
    return publicKey.rawData().toByteArray()
}