package jp.co.soramitsu.substrate_sdk.scale

import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.BoolOptionalWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.BoolWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.CompactBigIntWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.CompactUIntWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.LongWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.UInt16Writer
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.UInt32Writer
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.ULong32Writer
import okio.Buffer

class ScaleCodecWriter(
    private val outputStream: Buffer
) {

    @Throws(IOException::class)
    fun writeUint256(value: ByteArray) {
        require(value.size == 32) { "Value must be 32 byte array" }
        writeByteArray(value)
    }

    @Throws(IOException::class)
    fun writeByteArray(value: ByteArray) {
        writeCompact(value.size)
        outputStream.write(value)
    }

    @Throws(IOException::class)
    fun writeAsList(value: ByteArray) {
        writeCompact(value.size)
        outputStream.write(value, 0, value.size)
    }

    /**
     * Write the byte into output stream as-is directly, the input is supposed to be already encoded
     *
     * @param byte byte to write
     * @throws IOException if failed to write
     */
    @Throws(IOException::class)
    fun directWrite(byte: Int) {
        outputStream.writeByte(byte)
    }

    /**
     * Write the bytes into output stream as-is directly, the input is supposed to be already encoded
     *
     * @param bytes bytes to write
     * @param off offset
     * @param len length
     * @throws IOException if failed to write
     */
    @Throws(IOException::class)
    fun directWrite(bytes: ByteArray, off: Int, len: Int) {
        outputStream.write(bytes, off, len)
    }

    @Throws(IOException::class)
    fun flush() {
        outputStream.flush()
    }

    @Throws(IOException::class)
    fun close() {
        outputStream.close()
    }

    @Throws(IOException::class)
    fun <T> write(writer: ScaleWriter<T>, value: T) {
        writer.write(this, value)
    }

    @Throws(IOException::class)
    fun writeByte(byte: Int) {
        directWrite(byte)
    }

    @Throws(IOException::class)
    fun writeByte(value: Byte) {
        directWrite(value.toInt())
    }

    @Throws(IOException::class)
    fun writeUint16(value: Int) {
        UINT16.write(this, value)
    }

    @Throws(IOException::class)
    fun writeUint32(value: Int) {
        UINT32.write(this, value)
    }

    @Throws(IOException::class)
    fun writeUint32(value: Long) {
        ULONG32.write(this, value)
    }

    @Throws(IOException::class)
    fun writeCompact(value: Int) {
        COMPACT_UINT.write(this, value)
    }

    @Throws(IOException::class)
    fun <T> writeOptional(scaleWriter: ScaleWriter<T>, value: T?) {
        if (scaleWriter is BoolOptionalWriter || scaleWriter is BoolWriter) {
            BOOL_OPT.write(this, value as Boolean?)
        } else {
            if (value == null) {
                BOOL.write(this, false)
            } else {
                BOOL.write(this, true)
                scaleWriter.write(this, value)
            }
        }
    }

    @Throws(IOException::class)
    fun writeString(value: String) {
        val bytes: ByteArray = value.toByteArray(Charsets.UTF_8)
        writeByteArray(bytes)
    }

    fun writeLong(value: Long) {
        LONG.write(this, value)
    }

    companion object {
        val COMPACT_UINT = CompactUIntWriter()
        val COMPACT_BIGINT = CompactBigIntWriter()
        val UINT16 = UInt16Writer()
        val UINT32 = UInt32Writer()
        val ULONG32 = ULong32Writer()
        val BOOL = BoolWriter()
        val BOOL_OPT = BoolOptionalWriter()
        val LONG = LongWriter()
    }
}