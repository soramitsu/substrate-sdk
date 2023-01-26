package jp.co.soramitsu.substrate_sdk.encrypt

import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.extensions.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class HmacUtilsTest {

    private val SourceByteArray = "3132333435363738393031323334353637383930313233343536373839303132".fromHex()
    private val SecretByteArray = "f65a7d560102f2019da9b9d8993f53f51cc38d50cdff3d0b8e71997d7f911ff1f65a7d560102f2019da9b9d8993f53f51cc38d50cdff3d0b8e71997d7f911ff1".fromHex()

    @Test
    fun `check hmac256`() {
        val actual = SourceByteArray.hmacSha256(SecretByteArray).toHexString()
        val expected = "5d38707198258f6b83cd4fd2a061f7d67822e4bca7c7cfd2d767b3901440abcb"
        assertEquals(actual = actual, expected = expected)
    }

    @Test
    fun `check hmac512`() {
        val actual = SourceByteArray.hmacSha512(SecretByteArray).toHexString()
        val expected = "3f0b62c87e7b8e559b5d78752034f77af4c0a022745aef75a5e8e68a7fee0d1aa958dd9740c3c1081414556408bfac5d83e9040590959fd16f8c5029f074e434"
        assertEquals(actual = actual, expected = expected)
    }
}