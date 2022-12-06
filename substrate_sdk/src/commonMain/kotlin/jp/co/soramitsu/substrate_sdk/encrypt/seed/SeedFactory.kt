package jp.co.soramitsu.substrate_sdk.encrypt.seed

import jp.co.soramitsu.substrate_sdk.encrypt.mnemonic.Mnemonic

interface SeedFactory {

    class Result(val seed: ByteArray, val mnemonic: Mnemonic)

    fun createSeed(length: Mnemonic.Length, password: String?): Result

    fun deriveSeed(mnemonicWords: String, password: String?): Result
}
