package jp.co.soramitsu.substrate_sdk.hash

import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.extensions.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class KeccakTest {

    @Test
    fun `check keccak256`() {
        val byteArray = "e2db688cbc776a7711c573eb1edeeb76ead9fb26e14f78467a3c17620bf72925".fromHex()
        val result = byteArray.keccak256()
        assertEquals(
            expected = "b607ea97c8c0dfe6ea78d56d2f1046362f9f43edf208b5b500259cf4f77d82b0",
            actual = result.toHexString()
        )
    }
}