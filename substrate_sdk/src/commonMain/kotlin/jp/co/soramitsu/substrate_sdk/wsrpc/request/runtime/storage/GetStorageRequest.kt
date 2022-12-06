package jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.storage

import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.RuntimeRequest

open class GetStorageRequest(keys: List<String>) : RuntimeRequest(
    method = "state_getStorage",
    keys
)
