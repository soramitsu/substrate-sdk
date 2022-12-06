package jp.co.soramitsu.substrate_sdk.encrypt.mnemonic

import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.extensions.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class MnemonicCreatorTest {

    private val expectedEntropyHex = "2a5ecdeb7466f14d3c06d5aa5c6d433d"
    private val expectedMnemonicWords = "clean wait kiss trip humor pledge useless survey prevent toddler express knock"

    @Test
    fun `should generate mnemonic of different length 12`() {
        val mnemonic = MnemonicCreatorInstance.randomMnemonic(Mnemonic.Length.TWELVE)
        assertEquals(12,  mnemonic.wordList.size)
    }

    @Test
    fun `should generate mnemonic of different length 21`() {
        val mnemonic = MnemonicCreatorInstance.randomMnemonic(Mnemonic.Length.TWENTY_ONE)
        assertEquals(21, mnemonic.wordList.size)
    }

    @Test
    fun `should generate mnemonic from entropy`() {
       runMnemonicDecodingTest(expectedMnemonicWords)
    }

    @Test
    fun generateMnemonicFromEntropy() {
        val mnemonic = MnemonicCreatorInstance.fromEntropy(expectedEntropyHex.fromHex())

        assertEquals(expectedMnemonicWords, mnemonic.words)
    }

    @Test
    fun `should generate mnemonic from input with extra special symbols`() {
        runMnemonicDecodingTest(" clean wait kiss trip humor pledge useless survey prevent toddler express knock \t,")
        runMnemonicDecodingTest("\t clean wait kiss trip humor pledge useless survey prevent toddler express knock")
        runMnemonicDecodingTest("\n clean wait kiss trip humor pledge useless survey prevent toddler express knock")
        runMnemonicDecodingTest("clean, wait\t kiss    trip,    humor\n pledge useless survey prevent toddler express knock")
    }

    private fun runMnemonicDecodingTest(words: String) {
        val mnemonic = MnemonicCreatorInstance.fromWords(words)

        assertEquals(expectedEntropyHex, mnemonic.entropy.toHexString())
        assertEquals(expectedMnemonicWords, mnemonic.words)
    }
}