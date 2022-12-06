package jp.co.soramitsu.substrate_sdk.wsrpc.logging

object EmptyLogger: Logger {
    override fun log(message: String?) {
    }

    override fun log(throwable: Throwable?) {
    }
}
