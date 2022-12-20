package jp.co.soramitsu.substrate_sdk.runtime.metadata

import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.runtime.metadata.v14.RuntimeMetadataSchemaV14
import jp.co.soramitsu.substrate_sdk.scale.EncodableStruct
import jp.co.soramitsu.substrate_sdk.scale.Schema
import jp.co.soramitsu.substrate_sdk.scale.uint32
import jp.co.soramitsu.substrate_sdk.scale.uint8

object Magic : Schema<Magic>() {
    val magicNumber by uint32()
    val runtimeVersion by uint8()
}

class RuntimeMetadataReader private constructor(
    val metadataVersion: Int,
    val metadata: EncodableStruct<*>
) {

    companion object {

        @OptIn(ExperimentalUnsignedTypes::class)
        fun read(metadataScale: String): RuntimeMetadataReader {

            val metadataScaleByteArray = metadataScale.fromHex()

            val runtimeVersion = Magic.read(metadataScaleByteArray)[Magic.runtimeVersion].toInt()

            val metadata = when {
                runtimeVersion < 14 -> {
                    RuntimeMetadataSchema.read(metadataScaleByteArray)
                }
                else -> {
                    RuntimeMetadataSchemaV14.read(metadataScaleByteArray)
                }
            }

            return RuntimeMetadataReader(
                metadataVersion = runtimeVersion,
                metadata = metadata
            )
        }
    }
}
