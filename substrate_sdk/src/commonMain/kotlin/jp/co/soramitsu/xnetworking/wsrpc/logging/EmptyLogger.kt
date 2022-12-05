package jp.co.soramitsu.xnetworking.wsrpc.logging

object EmptyLogger: Logger {
    override fun log(message: String?) {
    }

    override fun log(throwable: Throwable?) {
    }
}
