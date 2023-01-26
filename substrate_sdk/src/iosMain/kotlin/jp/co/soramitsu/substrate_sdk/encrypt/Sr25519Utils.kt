package jp.co.soramitsu.substrate_sdk.encrypt

import cocoapods.IrohaCrypto.*
import jp.co.soramitsu.substrate_sdk.common.toByteArray
import jp.co.soramitsu.substrate_sdk.common.toData

actual fun convert_Sr25519_to_Ed25519_Bytes(bytes: ByteArray): ByteArray {
    return SNPrivateKey(rawData = bytes.toData(), null).toEd25519Data().toByteArray()
}

actual fun convert_Ed25519_to_Sr25519_Bytes(bytes: ByteArray): ByteArray {
    return SNPrivateKey(fromEd25519 = bytes.toData(), null).rawData().toByteArray()
}