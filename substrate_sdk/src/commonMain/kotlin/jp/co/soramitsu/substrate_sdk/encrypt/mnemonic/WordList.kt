package jp.co.soramitsu.substrate_sdk.encrypt.mnemonic

expect interface WordList {

    fun getWord(index: Int): String

    fun getSpace(): Char
}