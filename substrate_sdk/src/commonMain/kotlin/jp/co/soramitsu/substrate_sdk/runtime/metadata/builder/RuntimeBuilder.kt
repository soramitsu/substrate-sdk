package jp.co.soramitsu.substrate_sdk.runtime.metadata.builder

import jp.co.soramitsu.substrate_sdk.runtime.definitions.registry.TypeRegistry
import jp.co.soramitsu.substrate_sdk.runtime.metadata.RuntimeMetadata
import jp.co.soramitsu.substrate_sdk.runtime.metadata.RuntimeMetadataReader

interface RuntimeBuilder {

    fun buildMetadata(reader: RuntimeMetadataReader, typeRegistry: TypeRegistry): RuntimeMetadata
}

object VersionedRuntimeBuilder : RuntimeBuilder {

    override fun buildMetadata(
        reader: RuntimeMetadataReader,
        typeRegistry: TypeRegistry
    ): RuntimeMetadata {
        return when (reader.metadataVersion) {
            14 -> V14RuntimeBuilder.buildMetadata(reader, typeRegistry)
            else -> V13RuntimeBuilder.buildMetadata(reader, typeRegistry)
        }
    }
}
