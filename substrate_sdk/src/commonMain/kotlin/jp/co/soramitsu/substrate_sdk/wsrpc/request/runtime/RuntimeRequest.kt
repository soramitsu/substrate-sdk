package jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime

import jp.co.soramitsu.substrate_sdk.wsrpc.request.base.RpcRequest
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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
