package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class UInt64Reader : ScaleReader<BigInteger> {

    override fun read(reader: ScaleCodecReader): BigInteger {
        var result = BigInteger.ZERO
        result = result.add(BigInteger.fromLong(reader.readUByte().toLong()))
        result = result.add(BigInteger.fromLong(reader.readUByte().toLong()).shl(8))
        result = result.add(BigInteger.fromLong(reader.readUByte().toLong()).shl(16))
        result = result.add(BigInteger.fromLong(reader.readUByte().toLong()).shl(24))
        result = result.add(BigInteger.fromLong(reader.readUByte().toLong()).shl(32))
        result = result.add(BigInteger.fromLong(reader.readUByte().toLong()).shl(40))
        result = result.add(BigInteger.fromLong(reader.readUByte().toLong()).shl(48))
        result = result.add(BigInteger.fromLong(reader.readUByte().toLong()).shl(56))
        return result
    }
}