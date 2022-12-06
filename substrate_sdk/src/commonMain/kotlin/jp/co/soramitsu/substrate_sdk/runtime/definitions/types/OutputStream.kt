package jp.co.soramitsu.substrate_sdk.runtime.definitions.types

import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException

expect abstract class OutputStream() {

    @Throws(IOException::class)
    fun write(byteArray: ByteArray)
}