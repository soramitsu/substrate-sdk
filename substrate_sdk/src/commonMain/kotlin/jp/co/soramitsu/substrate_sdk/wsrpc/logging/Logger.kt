package jp.co.soramitsu.substrate_sdk.wsrpc.logging

interface Logger {
    fun log(message: String?)

    fun log(throwable: Throwable?)
}
