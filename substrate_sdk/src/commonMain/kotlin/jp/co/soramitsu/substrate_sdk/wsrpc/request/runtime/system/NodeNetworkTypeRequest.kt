package jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.system

import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.RuntimeRequest

private const val METHOD = "system_chain"

class NodeNetworkTypeRequest : RuntimeRequest(METHOD, listOf())
