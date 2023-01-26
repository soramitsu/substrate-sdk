package jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate

import cocoapods.IrohaCrypto.EDKeyFactory
import cocoapods.IrohaCrypto.SECKeyFactory
import cocoapods.IrohaCrypto.SNKeyFactory
import cocoapods.IrohaCrypto.SNKeypair
import jp.co.soramitsu.substrate_sdk.common.toByteArray
import jp.co.soramitsu.substrate_sdk.common.toData
import jp.co.soramitsu.substrate_sdk.encrypt.keypair.BaseKeypair
import jp.co.soramitsu.substrate_sdk.hash.blake2b256
import jp.co.soramitsu.substrate_sdk.hash.blake2b32
import jp.co.soramitsu.substrate_sdk.scale.dataType.ScaleEncoder
import jp.co.soramitsu.substrate_sdk.scale.dataType.stringScale

actual class Ed25519SubstrateKeypairFactory: BaseEd25519SubstrateKeypairFactory() {

    private val hdkdPrefix = "Ed25519HDKD"
    private val internalFactory = EDKeyFactory()

    actual override fun generateBaseKeypair(seed: ByteArray): BaseKeypair {
        val s = (stringScale.encode(hdkdPrefix) + seed).blake2b32()
        val d = internalFactory.deriveFromSeed(seed = s.toData(), error = null)!!
        return BaseKeypair(
            publicKey = d.publicKey().rawData().toByteArray(),
            privateKey = d.privateKey().rawData().toByteArray(),
        )
        // TODO!
    }
}