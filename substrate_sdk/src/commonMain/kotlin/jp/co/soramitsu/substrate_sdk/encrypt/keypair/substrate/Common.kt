package jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate

import jp.co.soramitsu.substrate_sdk.encrypt.junction.Junction
import jp.co.soramitsu.substrate_sdk.encrypt.junction.JunctionType
import jp.co.soramitsu.substrate_sdk.encrypt.keypair.Keypair
import jp.co.soramitsu.substrate_sdk.encrypt.keypair.KeypairFactory
import jp.co.soramitsu.substrate_sdk.hash.blake2b256
import jp.co.soramitsu.substrate_sdk.scale.dataType.stringScale

class KeypairWithSeed(
    val seed: ByteArray,
    override val privateKey: ByteArray,
    override val publicKey: ByteArray
) : Keypair

abstract class OtherSubstrateKeypairFactory: KeypairFactory<KeypairWithSeed> {

    abstract val hardDerivationPrefix: String

    override fun deriveChild(parent: KeypairWithSeed, junction: Junction): KeypairWithSeed {
        if (junction.type == JunctionType.HARD) {
            val prefix = stringScale.encode(hardDerivationPrefix)
            val newSeed = (prefix + parent.seed + junction.chaincode).blake2b256()
            return deriveFromSeed(newSeed)
        } else {
            throw KeypairFactory.SoftDerivationNotSupported()
        }
    }
}