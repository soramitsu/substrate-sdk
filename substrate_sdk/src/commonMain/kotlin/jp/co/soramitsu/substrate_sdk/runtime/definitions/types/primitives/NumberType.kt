package jp.co.soramitsu.substrate_sdk.runtime.definitions.types.primitives

import com.ionspin.kotlin.bignum.integer.BigInteger

abstract class NumberType(name: String) : Primitive<BigInteger>(name) {

    override fun isValidInstance(instance: Any?): Boolean {
        return instance is BigInteger
    }
}
