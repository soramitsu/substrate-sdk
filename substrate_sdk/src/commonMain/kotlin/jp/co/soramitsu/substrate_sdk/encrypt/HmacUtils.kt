package jp.co.soramitsu.substrate_sdk.encrypt

expect fun ByteArray.hmacSHA256(secret: ByteArray): ByteArray

expect fun ByteArray.hmacSHA512(secret: ByteArray): ByteArray
