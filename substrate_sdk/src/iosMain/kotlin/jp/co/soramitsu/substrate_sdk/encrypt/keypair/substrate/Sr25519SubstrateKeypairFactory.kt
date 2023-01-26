package jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate

import cocoapods.IrohaCrypto.*
import jp.co.soramitsu.substrate_sdk.common.toByteArray
import jp.co.soramitsu.substrate_sdk.common.toData

actual class Sr25519SubstrateKeypairFactory: BaseSr25519SubstrateKeypairFactory() {

    private val internalFactory = SNKeyFactory()

    actual override fun keypairFromSeed(seed: ByteArray): ByteArray {
        return internalFactory.createKeypairFromSeed(seed = seed.toData(), null)!!
            .rawData()
            .toByteArray()
    }

    actual override fun deriveKeypairSoftBytes(keypair: ByteArray, chaincode: ByteArray): ByteArray {
        val snKeypair = SNKeypair(rawData = keypair.toData(), null)
        return internalFactory.createKeypairSoft(
            parent = snKeypair,
            chaincode = chaincode.toData(),
            error = null
        )!!.rawData().toByteArray()
    }

    actual override fun deriveKeypairHardBytes(keypair: ByteArray, chaincode: ByteArray): ByteArray {
        val snKeypair = SNKeypair(rawData = keypair.toData(), null)
        return internalFactory.createKeypairHard(
            parent = snKeypair,
            chaincode = chaincode.toData(),
            error = null
        )!!.rawData().toByteArray()
    }
}
