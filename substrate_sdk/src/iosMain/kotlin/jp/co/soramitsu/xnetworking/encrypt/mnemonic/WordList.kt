package jp.co.soramitsu.xnetworking.encrypt.mnemonic

actual interface WordList {

    actual fun getWord(index: Int): String

    actual fun getSpace(): Char
}