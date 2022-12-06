package jp.co.soramitsu.substrate_sdk.hash

import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.extensions.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class XXHashTest {

    @Test
    fun `check xxHash64`() {
        val byteArray = "e2db688cbc776a7711c573eb1edeeb76ead9fb26e14f78467a3c17620bf72925".fromHex()
        val result = byteArray.xxHash64()
        assertEquals(
            expected = 5794077967867516868,
            actual = result
        )
    }

    @Test
    fun `check xxHash64Concat`() {
        val byteArray = "e2db688cbc776a7711c573eb1edeeb76ead9fb26e14f78467a3c17620bf72925".fromHex()
        val result = byteArray.xxHash64Concat().toHexString()
        assertEquals(
            expected = "c417a9a336b36850e2db688cbc776a7711c573eb1edeeb76ead9fb26e14f78467a3c17620bf72925",
            actual = result
        )
    }

    @Test
    fun `check xxHash128`() {
        val byteArray = "e2db688cbc776a7711c573eb1edeeb76ead9fb26e14f78467a3c17620bf72925".fromHex()
        val result = byteArray.xxHash128().toHexString()
        assertEquals(
            expected = "c417a9a336b36850cfdbc40ffff7afc5",
            actual = result
        )
    }

    @Test
    fun `check xxHash256`() {
        val byteArray = "e2db688cbc776a7711c573eb1edeeb76ead9fb26e14f78467a3c17620bf72925".fromHex()
        val result = byteArray.xxHash256().toHexString()
        assertEquals(
            expected = "c417a9a336b36850cfdbc40ffff7afc59e74cfef2f89f219d042ef241f0853e4",
            actual = result
        )
    }
}