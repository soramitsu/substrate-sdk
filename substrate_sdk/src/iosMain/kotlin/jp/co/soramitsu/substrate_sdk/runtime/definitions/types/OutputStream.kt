package jp.co.soramitsu.substrate_sdk.runtime.definitions.types

import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException

actual abstract class OutputStream {

    @Throws(IOException::class)
    actual fun write(byteArray: ByteArray) {
        TODO()
    }
}