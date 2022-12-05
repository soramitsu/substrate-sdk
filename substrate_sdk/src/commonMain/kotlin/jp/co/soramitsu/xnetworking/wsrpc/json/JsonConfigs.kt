package jp.co.soramitsu.xnetworking.wsrpc.json

import jp.co.soramitsu.xnetworking.wsrpc.request.runtime.AnyAsRequestParamsSerializer
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