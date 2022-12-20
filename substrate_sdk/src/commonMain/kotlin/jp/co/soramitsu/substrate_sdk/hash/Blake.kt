package jp.co.soramitsu.substrate_sdk.hash

import com.appmattus.crypto.Algorithm
import com.appmattus.crypto.Digest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private val blake2b_128 by lazy { Algorithm.Blake2b(128).createDigest() }
private val blake2b_256 by lazy { Algorithm.Blake2b(256).createDigest() }
private val blake2b_512 by lazy { Algorithm.Blake2b(512).createDigest() }
private val mutex = Mutex()

fun ByteArray.blake2b128(): ByteArray {
    return threadSafe { blake2b_128.digest(this) }
}

fun ByteArray.blake2b128Concat(): ByteArray {
    return threadSafe { blake2b_128.hashConcatDigest(this) }
}

fun ByteArray.blake2b256(): ByteArray {
    return threadSafe { blake2b_256.digest(this) }
}

fun ByteArray.blake2b512(): ByteArray {
    return threadSafe { blake2b_512.digest(this) }
}

private inline fun <T> threadSafe(crossinline block: () -> T): T = runBlocking {
    return@runBlocking mutex.withLock {
        block.invoke()
    }
}

private fun Digest<*>.hashConcatDigest(bytes: ByteArray): ByteArray {
    return digest(bytes) + bytes
}