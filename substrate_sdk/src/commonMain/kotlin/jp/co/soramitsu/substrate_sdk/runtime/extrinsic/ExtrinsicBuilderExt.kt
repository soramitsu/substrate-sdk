package jp.co.soramitsu.substrate_sdk.runtime.extrinsic

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.runtime.definitions.types.composite.DictEnum

fun ExtrinsicBuilder.transfer(recipientAccountId: ByteArray, amount: BigInteger): ExtrinsicBuilder {
    return call(
        moduleName = "Balances",
        callName = "transfer",
        arguments = mapOf(
            "dest" to DictEnum.Entry(
                name = "Id",
                value = recipientAccountId
            ),
            "value" to amount
        )
    )
}
