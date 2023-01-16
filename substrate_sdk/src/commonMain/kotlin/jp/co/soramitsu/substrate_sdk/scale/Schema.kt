package jp.co.soramitsu.substrate_sdk.scale

import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.extensions.toHexString
import jp.co.soramitsu.substrate_sdk.scale.dataType.OptionalScaleType
import jp.co.soramitsu.substrate_sdk.scale.dataType.ScaleTransformer
import okio.Buffer

abstract class BaseSchema<S : Schema<S>> {

    companion object;

    internal val fields: MutableList<Field<*>> = mutableListOf()

    fun <T> field(dataType: ScaleTransformer<T>, default: T?): Field<T> {
        val field = Field(dataType, default)

        fields += field

        return field
    }

    fun <T> nullableField(dataType: OptionalScaleType<T>, default: T?): Field<T?> {
        val field = Field(dataType, default)

        fields += field

        return field
    }

    fun readOrNull(source: String): EncodableStruct<S>? {
        return try {
            read(source.fromHex())
        } catch (_: Exception) {
            return null
        }
    }

    fun read(scale: String): EncodableStruct<S> {
        return read(scale.fromHex())
    }

    abstract fun read(bytes: ByteArray): EncodableStruct<S>

    abstract fun toByteArray(struct: EncodableStruct<S>): ByteArray

    fun toHexString(struct: EncodableStruct<S>): String =
        toByteArray(struct).toHexString(withPrefix = true)
}

abstract class Schema<S : Schema<S>> :
    BaseSchema<S>(),
    ScaleReader<EncodableStruct<S>>,
    ScaleWriter<EncodableStruct<S>> {

    override fun read(bytes: ByteArray): EncodableStruct<S> {
        val reader = ScaleCodecReader(bytes)
        return read(reader)
    }

    override fun read(reader: ScaleCodecReader): EncodableStruct<S> {
        val struct = EncodableStruct(this as S)

        for (field in fields) {
            val value = field.dataType.read(reader)
            struct[field as Field<Any?>] = value
        }

        return struct
    }

    override fun toByteArray(struct: EncodableStruct<S>): ByteArray {
        val outputStream = Buffer()

        val writer = ScaleCodecWriter(outputStream)

        write(writer, struct)

        return outputStream.readByteArray()
    }

    override fun write(writer: ScaleCodecWriter, struct: EncodableStruct<S>) {
        for (field in fields) {
            val value = struct.fieldsWithValues[field]

            val type = field.dataType as ScaleTransformer<Any?>

            type.write(writer, value)
        }
    }
}
