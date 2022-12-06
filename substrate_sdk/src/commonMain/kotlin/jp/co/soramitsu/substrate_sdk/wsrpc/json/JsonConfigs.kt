package jp.co.soramitsu.substrate_sdk.wsrpc.json

import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.AnyAsRequestParamsSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

object JsonConfigs {
    val standard = Json {
        prettyPrint = false
        encodeDefaults = true
        isLenient = true
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            contextual(Any::class, AnyAsRequestParamsSerializer)
        }
    }
}