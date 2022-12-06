package jp.co.soramitsu.substrate_sdk.runtime.metadata

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.runtime.metadata.module.Module

interface WithName {
    val name: String
}

fun <T : WithName> List<T>.groupByName() = associateBy(WithName::name).toMap()

class RuntimeMetadata(
    val runtimeVersion: BigInteger,
    val modules: Map<String, Module>,
    val extrinsic: ExtrinsicMetadata
)

class ExtrinsicMetadata(
    val version: BigInteger,
    val signedExtensions: List<String>
)
