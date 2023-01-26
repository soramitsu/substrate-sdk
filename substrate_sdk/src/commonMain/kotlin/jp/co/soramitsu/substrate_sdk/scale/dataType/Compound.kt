@file:Suppress("ClassName")

package jp.co.soramitsu.substrate_sdk.scale.dataType

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.EncodableStruct
import jp.co.soramitsu.substrate_sdk.scale.Schema
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer.BoolWriter
import kotlin.reflect.KClass
import kotlin.reflect.cast

fun <A, B> tupleScale(
    a: ScaleTransformer<A>,
    b: ScaleTransformer<B>
) = TupleScaleType(a, b)

fun <T> optionalScale(
    dataType: ScaleTransformer<T>,
) = OptionalScaleType(dataType)

fun <T> listScale(
    dataType: ScaleTransformer<T>,
) = ListScaleType(dataType)

fun <S: Schema<S>> scalableScale(
    schema: Schema<S>
) = ScalableScaleType(schema)

fun <E: Enum<E>> enumScale(
    enumClass: KClass<E>,
    mappper: (Int) -> E
) = EnumScaleType(enumClass, mappper)

fun collectionEnumScale(
    values: List<String>
) = CollectionEnumScaleType(values)

fun unionScale(
    dataTypes: Array<out ScaleTransformer<*>>
) = UnionScaleType(dataTypes)

class TupleScaleType<A, B>(
    private val a: ScaleTransformer<A>,
    private val b: ScaleTransformer<B>
): ScaleTransformer<Pair<A, B>>() {

    override fun read(reader: ScaleCodecReader): Pair<A, B> {
        val a = a.read(reader)
        val b = b.read(reader)
        return a to b
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Pair<A, B>) {
        a.write(scaleCodecWriter, value.first)
        b.write(scaleCodecWriter, value.second)
    }

    override fun conformsType(value: Any?): Boolean {
        return  value is Pair<*, *> &&
                a.conformsType(value.first) &&
                b.conformsType(value.second)
    }
}

class OptionalScaleType<T>(
    private val dataType: ScaleTransformer<T>
): ScaleTransformer<T?>() {

    override fun read(reader: ScaleCodecReader): T? {
        if (dataType is BooleanScaleType) {
            return when (reader.readByte().toInt()) {
                0 -> null
                1 -> false as T?
                2 -> true as T?
                else -> throw IllegalArgumentException("Not a optional boolean")
            }
        }

        val some: Boolean = reader.readBoolean()

        return if (some) {
            dataType.read(reader)
        } else {
            null
        }
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: T?) {
        if (dataType is BooleanScaleType) {
            scaleCodecWriter.writeOptional(BoolWriter(), value as Boolean)
        } else {
            scaleCodecWriter.writeOptional(dataType, value)
        }
    }

    override fun conformsType(value: Any?): Boolean {
        return value == null ||
                dataType.conformsType(value)
    }
}

class ListScaleType<T>(
    private val dataType: ScaleTransformer<T>
): ScaleTransformer<List<T>>() {

    override fun read(reader: ScaleCodecReader): List<T> {
        val size = compactIntScale.read(reader)
        val result = mutableListOf<T>()

        repeat(size.intValue(exactRequired = false)) {
            val element = dataType.read(reader)
            result.add(element)
        }

        return result
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: List<T>) {
        val size = BigInteger.fromInt(value.size)
        compactIntScale.write(scaleCodecWriter, size)

        val androidDataType = dataType
        value.forEach {
            androidDataType.write(scaleCodecWriter, it)
        }
    }

    override fun conformsType(value: Any?): Boolean {
        return value is List<*> && value.all(dataType::conformsType)
    }
}

class ScalableScaleType<S : Schema<S>>(
    private val schema: Schema<S>
): ScaleTransformer<EncodableStruct<S>>() {

    override fun read(reader: ScaleCodecReader): EncodableStruct<S> {
        return schema.read(reader)
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: EncodableStruct<S>) {
        schema.write(scaleCodecWriter, value)
    }

    override fun conformsType(value: Any?): Boolean {
        return value is EncodableStruct<*> &&
                value.schema == schema
    }
}

class EnumScaleType<E : Enum<E>>(
    private val enumClass: KClass<E>,
    private val mappper: (Int) -> E
): ScaleTransformer<E>() {

    override fun read(reader: ScaleCodecReader): E {
        val index = reader.readByte().toInt()
        return mappper.invoke(index)
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: E) {
        scaleCodecWriter.writeByte(value.ordinal)
    }

    override fun conformsType(value: Any?): Boolean {
        if (value == null) return false

        return value::class == enumClass
    }
}

class CollectionEnumScaleType(
    private val values: List<String>
): ScaleTransformer<String>() {

    override fun read(reader: ScaleCodecReader): String {
        val index = reader.readByte()
        return values[index.toInt()]
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: String) {
        val index = values.indexOf(value)

        if (index == -1) {
            throw IllegalArgumentException("No $value in $values")
        }

        scaleCodecWriter.writeByte(index)
    }

    override fun conformsType(value: Any?): Boolean {
        return value is String
    }
}

class UnionScaleType(
    private val dataTypes: Array<out ScaleTransformer<*>>
): ScaleTransformer<Any?>() {

    override fun read(reader: ScaleCodecReader): Any? {
        val typeIndex = reader.readByte()
        val type = dataTypes[typeIndex.toInt()]
        return type.read(reader)
    }

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Any?) {
        val typeIndex = dataTypes.indexOfFirst { it.conformsType(value) }

        if (typeIndex == -1) {
            throw IllegalArgumentException(
                "Unknown type ${value?.let { it::class }} for this enum. Supported: ${
                    dataTypes.joinToString(
                        ", "
                    )
                }"
            )
        }

        val type = dataTypes[typeIndex] as ScaleTransformer<Any?>

        scaleCodecWriter.write(uInt8Scale, typeIndex.toUByte())
        scaleCodecWriter.write(type, value)
    }

    override fun conformsType(value: Any?): Boolean {
        return dataTypes.any { it.conformsType(value) }
    }
}