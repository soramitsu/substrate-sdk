package jp.co.soramitsu.substrate_sdk.encrypt.mnemonic

actual interface WordList {

    actual fun getWord(index: Int): String

    actual fun getSpace(): Char
}