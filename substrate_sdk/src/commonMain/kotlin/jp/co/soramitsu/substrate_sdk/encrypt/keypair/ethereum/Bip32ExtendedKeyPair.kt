package jp.co.soramitsu.substrate_sdk.encrypt.keypair.ethereum

import jp.co.soramitsu.substrate_sdk.encrypt.keypair.Keypair

class Bip32ExtendedKeyPair(
    override val privateKey: ByteArray,
    override val publicKey: ByteArray,
    val chaincode: ByteArray
) : Keypair
