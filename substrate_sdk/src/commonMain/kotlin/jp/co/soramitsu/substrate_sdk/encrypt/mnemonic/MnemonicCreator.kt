package jp.co.soramitsu.substrate_sdk.encrypt.mnemonic

val DELIMITER_REGEX = "[\\s,]+".toRegex()
val SPACE = EnglishWordList.INSTANCE.getSpace().toString()

val MnemonicCreatorInstance by lazy { MnemonicCreator() }

expect class MnemonicCreator() {

    fun randomMnemonic(length: Mnemonic.Length): Mnemonic

    fun fromWords(words: String): Mnemonic

    fun fromEntropy(entropy: ByteArray): Mnemonic

}