package jp.co.soramitsu.substrate_sdk.wsrpc.exception

class ConnectionClosedException : Exception() {

    override fun toString(): String = this::class.simpleName.orEmpty()
}
