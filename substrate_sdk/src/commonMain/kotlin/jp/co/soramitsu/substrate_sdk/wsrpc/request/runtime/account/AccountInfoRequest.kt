package jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.account

import jp.co.soramitsu.substrate_sdk.runtime.Module
import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.storage.GetStorageRequest

class AccountInfoRequest(publicKey: ByteArray) : GetStorageRequest(
    listOf(
        Module.System.Account.storageKey(publicKey)
    )
)
