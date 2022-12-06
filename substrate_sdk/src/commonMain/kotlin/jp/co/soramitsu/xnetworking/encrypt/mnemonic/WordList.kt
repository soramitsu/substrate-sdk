package jp.co.soramitsu.xnetworking.encrypt.mnemonic

expect interface WordList {

    fun getWord(index: Int): String

    fun getSpace(): Char
}