package jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.author

import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.RuntimeRequest

private const val METHOD = "author_pendingExtrinsics"

class PendingExtrinsicsRequest : RuntimeRequest(METHOD, listOf())
