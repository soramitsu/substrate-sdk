package jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.chain

import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.RuntimeRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val METHOD = "chain_getRuntimeVersion"

class RuntimeVersionRequest : RuntimeRequest(METHOD, listOf())

@Serializable
class RuntimeVersion(
    @SerialName("specVersion")
    val specVersion: Int,
    @SerialName("transactionVersion")
    val transactionVersion: Int
)
