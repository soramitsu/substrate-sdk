package jp.co.soramitsu.xnetworking.wsrpc.mappers

import jp.co.soramitsu.xnetworking.wsrpc.request.runtime.chain.RuntimeVersion

class RuntimeVersionMapper: POJOMapper<RuntimeVersion>(RuntimeVersion::class)