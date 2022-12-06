package jp.co.soramitsu.substrate_sdk.common

import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.OutputStream

expect class ByteArrayOutputStream(): OutputStream {

    fun toByteArray(): ByteArray
}