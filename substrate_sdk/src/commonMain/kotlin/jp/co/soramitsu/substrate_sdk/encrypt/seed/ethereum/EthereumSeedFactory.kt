package jp.co.soramitsu.substrate_sdk.encrypt.seed.ethereum

import jp.co.soramitsu.substrate_sdk.encrypt.mnemonic.Mnemonic
import jp.co.soramitsu.substrate_sdk.encrypt.mnemonic.MnemonicCreatorInstance
import jp.co.soramitsu.substrate_sdk.encrypt.seed.SeedCreatorInstance
import jp.co.soramitsu.substrate_sdk.encrypt.seed.SeedFactory

object EthereumSeedFactory : SeedFactory {

    override fun createSeed(length: Mnemonic.Length, password: String?): SeedFactory.Result {
        val mnemonic = MnemonicCreatorInstance.randomMnemonic(length)
        val seed = SeedCreatorInstance.deriveSeed(mnemonic.words.encodeToByteArray(), password)

        return SeedFactory.Result(seed, mnemonic)
    }

    override fun deriveSeed(mnemonicWords: String, password: String?): SeedFactory.Result {
        val mnemonic = MnemonicCreatorInstance.fromWords(mnemonicWords)
        val seed = SeedCreatorInstance.deriveSeed(mnemonic.words.encodeToByteArray(), password)

        return SeedFactory.Result(seed, mnemonic)
    }
}