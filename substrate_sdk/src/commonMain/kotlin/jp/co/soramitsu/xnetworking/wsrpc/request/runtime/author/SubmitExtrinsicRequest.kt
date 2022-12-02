package jp.co.soramitsu.xnetworking.wsrpc.request.runtime.author

import jp.co.soramitsu.xnetworking.wsrpc.request.runtime.RuntimeRequest

class SubmitExtrinsicRequest(extrinsic: String) : RuntimeRequest(
    method = "author_submitExtrinsic",
    params = listOf(extrinsic)
)

class SubmitAndWatchExtrinsicRequest(extrinsic: String) : RuntimeRequest(
    method = "author_submitAndWatchExtrinsic",
    params = listOf(extrinsic)
)
