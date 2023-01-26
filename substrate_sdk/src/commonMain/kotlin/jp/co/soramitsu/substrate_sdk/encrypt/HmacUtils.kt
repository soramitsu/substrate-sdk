package jp.co.soramitsu.substrate_sdk.encrypt

import okio.Buffer
import okio.ByteString

fun ByteArray.hmacSha256(secret: ByteArray): ByteArray {
    return toBuffer().hmacSha256(secret.toByteString()).toByteArray()
}

fun ByteArray.hmacSha512(secret: ByteArray): ByteArray {
    return toBuffer().hmacSha512(secret.toByteString()).toByteArray()
}

private fun ByteArray.toBuffer(): Buffer {
    val buffer = Buffer()
    buffer.write(this)
    return buffer
}

private fun ByteArray.toByteString(): ByteString {
    val buffer = Buffer()
    buffer.write(this)
    return buffer.readByteString()
}