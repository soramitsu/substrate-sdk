package jp.co.soramitsu.xnetworking.hash

import com.appmattus.crypto.Algorithm
import com.ditchoom.buffer.ByteOrder
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.wrap
import okio.Buffer

private val xxHash64 by lazy { Algorithm.XXHash64(0) }

fun ByteArray.xxHash64(byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Long {
    return PlatformBuffer.wrap(xxHash64.hash(this), byteOrder).readLong()
}

fun ByteArray.xxHash128(): ByteArray {
    return xxHash(hashLengthBits = 128)
}

fun ByteArray.xxHash256(): ByteArray {
    return xxHash(hashLengthBits = 256)
}

fun ByteArray.xxHash64Concat(): ByteArray {
    val buffer = Buffer()
    buffer.writeLong(xxHash64(byteOrder = ByteOrder.LITTLE_ENDIAN))
    buffer.write(this)
    return buffer.readByteArray()
}

private fun ByteArray.xxHash(hashLengthBits: Int): ByteArray {
    require(hashLengthBits % 64 == 0)

    val timesToRepeat = hashLengthBits / 64

    val buffer = Buffer()

    (0 until timesToRepeat).map { seed ->
        val xxHashAlgorithm = Algorithm.XXHash64(seed.toLong())
        PlatformBuffer.wrap(xxHashAlgorithm.hash(this), ByteOrder.LITTLE_ENDIAN).readLong()
    }.onEach(buffer::writeLong)

    return buffer.readByteArray()
}