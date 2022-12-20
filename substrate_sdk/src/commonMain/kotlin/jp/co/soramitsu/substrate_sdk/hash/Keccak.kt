package jp.co.soramitsu.substrate_sdk.hash

import com.appmattus.crypto.Algorithm

private val keccak_256 by lazy { Algorithm.Keccak256.createDigest() }

fun ByteArray.keccak256(): ByteArray {
    return keccak_256.digest(this)
}