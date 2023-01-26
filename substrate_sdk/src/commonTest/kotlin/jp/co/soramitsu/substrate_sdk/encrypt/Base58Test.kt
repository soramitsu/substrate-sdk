package jp.co.soramitsu.substrate_sdk.encrypt

import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.extensions.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Base58Test {

    private val OriginalWords = "457109c1a88f".fromHex()
    private val IncorrectEncodedString = "abcdefghijkl"
    private val EncodedString = "badfsfrn"

    @Test
    fun `check Base58 encode`() {
        val actual = OriginalWords.encodeToBase58String()
        assertEquals(actual = actual, expected = EncodedString)
    }

    @Test
    fun `check Base58 decode fail with incorrect symbols`() {
        assertFailsWith<NumberFormatException> {
            IncorrectEncodedString.decodeBase58()
        }
    }

    @Test
    fun `check Base58 decode`() {
        val actual = EncodedString.decodeBase58()
        assertEquals(
            actual = actual.toHexString(),
            expected = OriginalWords.toHexString()
        )
    }
}