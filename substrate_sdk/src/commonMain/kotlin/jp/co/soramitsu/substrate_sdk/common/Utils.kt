package jp.co.soramitsu.substrate_sdk.common

object Utils {
    fun String.toDoubleNan(): Double? = this.toDoubleOrNull()?.let {
        if (it.isNaN()) null else it
    }
}
