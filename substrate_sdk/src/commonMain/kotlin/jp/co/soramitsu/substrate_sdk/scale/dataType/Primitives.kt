package jp.co.soramitsu.substrate_sdk.scale.dataType

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.BoolWriter
import okio.Buffer

interface ScaleEncoder<T> {
    fun encode(value: T): ByteArray
}

interface ScaleDecoder<T> {
    fun decode(bytes: ByteArray): T
}

abstract class ScaleTransformer<T> :
    ScaleEncoder<T>, ScaleWriter<T>,
    ScaleDecoder<T>, ScaleReader<T> {

    override fun encode(value: T): ByteArray {
        val stream = Buffer()
        val writer = ScaleCodecWriter(stream)
        write(writer, value)
        return stream.readByteArray()
    }

    override fun decode(bytes: ByteArray): T {
        return read(ScaleCodecReader(bytes))
    }

    abstract fun conformsType(value: Any?): Boolean
}

val stringScale by lazy { StringScaleType() }
val booleanScale by lazy { BooleanScaleType() }
val byteArrayScale by lazy { ByteArrayScaleType() }

fun byteArraySizedScale(length: Int) = ByteArraySizedScaleType(length)

class StringScaleType: ScaleTransformer<String>() {

    override fun conformsType(value: Any?): Boolean {
        return value is String
    }

    override fun read(reader: ScaleCodecReader): String {
        return reader.readString()
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: String) {
        scaleCodecWriter.writeString(value)
    }
}

class BooleanScaleType: ScaleTransformer<Boolean>() {

    override fun conformsType(value: Any?): Boolean {
        return value is Boolean
    }

    override fun read(reader: ScaleCodecReader): Boolean {
        return reader.readBoolean()
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Boolean) {
        scaleCodecWriter.write(BoolWriter(), value)
    }
}

class ByteArrayScaleType: ScaleTransformer<ByteArray>() {

    override fun conformsType(value: Any?): Boolean {
        return value is ByteArray
    }

    override fun read(reader: ScaleCodecReader): ByteArray {
        return reader.readByteArray()
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: ByteArray) {
        scaleCodecWriter.writeByteArray(value)
    }
}

class ByteArraySizedScaleType(
    private val length: Int
): ScaleTransformer<ByteArray>() {

    override fun conformsType(value: Any?): Boolean {
        return value is ByteArray
    }

    override fun read(reader: ScaleCodecReader): ByteArray {
        return reader.readByteArray(length)
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: ByteArray) {
        scaleCodecWriter.directWrite(value, 0, length)
    }
}