package jp.co.soramitsu.substrate_sdk.runtime.metadata

import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.RuntimeRequest

object GetMetadataRequest : RuntimeRequest("state_getMetadata", listOf())
