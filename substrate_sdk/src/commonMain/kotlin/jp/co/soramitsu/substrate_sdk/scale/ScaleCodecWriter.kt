package jp.co.soramitsu.substrate_sdk.scale

import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.OutputStream

expect class ScaleCodecWriter(outputStream: OutputStream) {

    fun <T> write(writer: ScaleWriter<T>, value: T)

    fun directWrite(bytes: ByteArray, off: Int, len: Int)

    fun writeByte(byte: Int)

    fun <T> writeOptional(scaleWriter: ScaleWriter<T>, value: T)
}