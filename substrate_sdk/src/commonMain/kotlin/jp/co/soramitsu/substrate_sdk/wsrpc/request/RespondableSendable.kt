package jp.co.soramitsu.substrate_sdk.wsrpc.request

import jp.co.soramitsu.substrate_sdk.wsrpc.SocketService
import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.RuntimeRequest
import jp.co.soramitsu.substrate_sdk.wsrpc.response.RpcResponse
import jp.co.soramitsu.substrate_sdk.wsrpc.state.SocketStateMachine

internal class RespondableSendable(
    val request: RuntimeRequest,
    override val deliveryType: DeliveryType,
    val callback: SocketService.ResponseListener<RpcResponse>
) : SocketStateMachine.Sendable {
    override val id: Int = request.id

    override fun toString(): String {
        return "Sendable($id)"
    }
}
