package jp.co.soramitsu.xnetworking.runtime.metadata.module

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.xnetworking.runtime.definitions.types.Type
import jp.co.soramitsu.xnetworking.runtime.metadata.StorageEntryModifier
import jp.co.soramitsu.xnetworking.runtime.metadata.StorageHasher
import jp.co.soramitsu.xnetworking.runtime.metadata.WithName

class Module(
    override val name: String,
    val storage: Storage?,
    val calls: Map<String, MetadataFunction>?,
    val events: Map<String, Event>?,
    val constants: Map<String, Constant>,
    val errors: Map<String, Error>,
    val index: BigInteger
) : WithName

class Storage(
    val prefix: String,
    val entries: Map<String, StorageEntry>
) {

    operator fun get(entry: String) = entries[entry]
}

class StorageEntry(
    override val name: String,
    val modifier: StorageEntryModifier,
    val type: StorageEntryType,
    val default: ByteArray,
    val documentation: List<String>,
    val moduleName: String
) : WithName

sealed class StorageEntryType(
    val value: Type<*>?
) {

    class Plain(value: Type<*>?) : StorageEntryType(value)

    class NMap(
        val keys: List<Type<*>?>,
        val hashers: List<StorageHasher>,
        value: Type<*>?
    ) : StorageEntryType(value)
}

class MetadataFunction(
    override val name: String,
    val arguments: List<FunctionArgument>,
    val documentation: List<String>,
    val index: Pair<Int, Int>
) : WithName

class FunctionArgument(
    override val name: String,
    val type: Type<*>?
) : WithName

class Event(
    override val name: String,
    val index: Pair<Int, Int>,
    val arguments: List<Type<*>?>,
    val documentation: List<String>
) : WithName

class Constant(
    override val name: String,
    val type: Type<*>?,
    val value: ByteArray,
    val documentation: List<String>
) : WithName

class Error(
    override val name: String,
    val documentation: List<String>
) : WithName
