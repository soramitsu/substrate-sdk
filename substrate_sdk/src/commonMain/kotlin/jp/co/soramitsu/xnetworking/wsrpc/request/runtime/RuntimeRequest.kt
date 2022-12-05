package jp.co.soramitsu.xnetworking.wsrpc.request.runtime

import jp.co.soramitsu.xnetworking.wsrpc.request.base.RpcRequest
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlin.random.Random

private fun nextId() = Random.nextInt(1, Int.MAX_VALUE)

@Serializable
open class RuntimeRequest(
    @SerialName("method")
    val method: String,
    @SerialName("params")
    val params: List<@Contextual Any>,
    @SerialName("id")
    val id: Int,
) : RpcRequest() {

    constructor(
        method: String,
        params: List<@Contextual Any>,
    ): this(
        method = method,
        params = params,
        id = nextId()
    )
}
