package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.generics

import jp.co.soramitsu.substrate_sdk.runtime.RuntimeSnapshot
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.Type
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.errors.EncodeDecodeException
import jp.co.soramitsu.substrate_sdk.runtime.metadata.callOrNull
import jp.co.soramitsu.substrate_sdk.runtime.metadata.module.FunctionArgument
import jp.co.soramitsu.substrate_sdk.runtime.metadata.module.MetadataFunction
import jp.co.soramitsu.substrate_sdk.runtime.metadata.module.Module
import jp.co.soramitsu.substrate_sdk.runtime.metadata.moduleOrNull
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.dataType.tupleScale
import jp.co.soramitsu.substrate_sdk.scale.dataType.uInt8Scale

object GenericCall : Type<GenericCall.Instance>("GenericCall") {

    class Instance(
        val module: Module,
        val function: MetadataFunction,
        val arguments: Map<String, Any?>
    )

    private val indexCoder = tupleScale(uInt8Scale, uInt8Scale)

    override val isFullyResolved = true

    override fun decode(scaleCodecReader: ScaleCodecReader, runtime: RuntimeSnapshot): Instance {
        val (moduleIndex, callIndex) = indexCoder.read(scaleCodecReader)
            .run { first.toInt() to second.toInt() }

        val (module, function) = getModuleAndFunctionOrThrow(runtime, moduleIndex, callIndex)

        val arguments = function.arguments.associate { argumentDefinition ->
            argumentDefinition.name to argumentDefinition.typeOrThrow()
                .decode(scaleCodecReader, runtime)
        }

        return Instance(module, function, arguments)
    }

    override fun encode(
        scaleCodecWriter: ScaleCodecWriter,
        runtime: RuntimeSnapshot,
        value: Instance
    ) = with(value) {
        val callIndex = value.function.index.run { first.toUByte() to second.toUByte() }

        indexCoder.write(scaleCodecWriter, callIndex)

        function.arguments.forEach { argumentDefinition ->
            argumentDefinition.typeOrThrow()
                .encodeUnsafe(scaleCodecWriter, runtime, arguments[argumentDefinition.name])
        }
    }

    override fun isValidInstance(instance: Any?): Boolean {
        return instance is Instance
    }

    private fun FunctionArgument.typeOrThrow() =
        type ?: throw EncodeDecodeException("Argument $name is not resolved")

    private fun getModuleAndFunctionOrThrow(
        runtime: RuntimeSnapshot,
        moduleIndex: Int,
        callIndex: Int
    ): Pair<Module, MetadataFunction> {
        val module =
            runtime.metadata.moduleOrNull(moduleIndex) ?: callNotFound(moduleIndex, callIndex)
        val call = module.callOrNull(callIndex) ?: callNotFound(moduleIndex, callIndex)

        return module to call
    }

    private fun callNotFound(moduleIndex: Int, callIndex: Int): Nothing {
        throw EncodeDecodeException("No call found for index ($moduleIndex, $callIndex)")
    }
}
