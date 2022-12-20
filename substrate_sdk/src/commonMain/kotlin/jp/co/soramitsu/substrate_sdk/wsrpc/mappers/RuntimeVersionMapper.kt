package jp.co.soramitsu.substrate_sdk.wsrpc.mappers

import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.chain.RuntimeVersion

class RuntimeVersionMapper: POJOMapper<RuntimeVersion>(RuntimeVersion::class)