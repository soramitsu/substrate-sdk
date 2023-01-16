package jp.co.soramitsu.substrate_sdk.scale.dataType

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import com.ditchoom.buffer.ByteOrder
import jp.co.soramitsu.substrate_sdk.extensions.fromUnsignedBytes
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader.CompactBigIntReader
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.CompactBigIntWriter

val byteScale by lazy { ByteScaleType() }
val uInt8Scale by lazy { UInt8ScaleType() }
val uInt16Scale by lazy { UInt16ScaleType() }
val uInt32Scale by lazy { UInt32ScaleType() }
val longScale by lazy { LongScaleType() }
val uInt128Scale by lazy { UInt128ScaleType() }
val uInt64Scale by lazy { UInt64ScaleType() }
val compactIntScale by lazy { CompactIntScaleType() }

fun uIntScale(size: Int) = UIntScaleType(size)

class UInt64ScaleType: UIntScaleType(8)

class UInt128ScaleType: UIntScaleType(16)

class ByteScaleType: ScaleTransformer<Byte>() {

    override fun read(reader: ScaleCodecReader): Byte {
        return reader.readByte()
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Byte) {
        scaleCodecWriter.writeByte(value)
    }

    override fun conformsType(value: Any?): Boolean {
        return value is Byte
    }
}

class UInt8ScaleType: ScaleTransformer<UByte>() {

    override fun read(reader: ScaleCodecReader): UByte {
        return reader.readUByte().toUByte()
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: UByte) {
        scaleCodecWriter.writeByte(value.toInt())
    }

    override fun conformsType(value: Any?): Boolean {
        return value is UByte
    }
}

class UInt16ScaleType: ScaleTransformer<Int>() {

    override fun read(reader: ScaleCodecReader): Int {
        return reader.readUint16()
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Int) {
        scaleCodecWriter.writeUint16(value)
    }

    override fun conformsType(value: Any?): Boolean {
        return value is Int
    }
}
 class UInt32ScaleType: ScaleTransformer<UInt>() {

    override fun read(reader: ScaleCodecReader): UInt {
        return reader.readUint32().toUInt()
    }
     override fun write(scaleCodecWriter: ScaleCodecWriter, value: UInt) {
         scaleCodecWriter.writeUint32(value.toLong())
     }

     override fun conformsType(value: Any?): Boolean {
        return value is UInt
    }
}

class LongScaleType: ScaleTransformer<Long>() {

    override fun read(reader: ScaleCodecReader): Long {
        return reader.readLong()
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Long) {
        scaleCodecWriter.writeLong(value)
    }

    override fun conformsType(value: Any?): Boolean {
        return value is Long
    }
}

open class UIntScaleType constructor(
    private val size: Int
): ScaleTransformer<BigInteger>() {

    override fun read(reader: ScaleCodecReader): BigInteger {
        val bytes = reader.readByteArray(size)
        return bytes.fromUnsignedBytes(ByteOrder.LITTLE_ENDIAN)
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: BigInteger) {
        val array = value.toByteArray()
        val padded = ByteArray(size)
        val startAt = padded.size - array.size
        array.copyInto(padded, startAt)
        scaleCodecWriter.directWrite(padded.reversedArray(), 0, size)
    }

    override fun conformsType(value: Any?): Boolean {
        return value is BigInteger
    }
}

class CompactIntScaleType: ScaleTransformer<BigInteger>() {

    private val compactIntReader = CompactBigIntReader()
    private val compactIntWriter = CompactBigIntWriter()

    override fun read(reader: ScaleCodecReader): BigInteger {
        return compactIntReader.read(reader)
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: BigInteger) {
        compactIntWriter.write(scaleCodecWriter, value)
    }

    override fun conformsType(value: Any?): Boolean {
        return value is BigInteger
    }
}