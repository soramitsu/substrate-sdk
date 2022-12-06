package jp.co.soramitsu.substrate_sdk.common.exceptions

expect open class IOException(message: String?, cause: Throwable?) : Exception {
    constructor(message: String? = null)
}