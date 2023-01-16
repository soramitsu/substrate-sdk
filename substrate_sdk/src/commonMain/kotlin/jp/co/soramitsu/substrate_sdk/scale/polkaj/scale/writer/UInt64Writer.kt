package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException

class UInt64Writer : ScaleWriter<BigInteger> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: BigInteger) {
        require(value >= BigInteger.ZERO) { "Negative values are not supported: $value" }
        require(value <= MAX_UINT64) { "Value is to big for 64 bits. $value" }
        scaleCodecWriter.directWrite(value.and(BigInteger.fromInt(255)).intValue())
        scaleCodecWriter.directWrite(value.shr(8).and(BigInteger.fromInt(255)).intValue())
        scaleCodecWriter.directWrite(value.shr(16).and(BigInteger.fromInt(255)).intValue())
        scaleCodecWriter.directWrite(value.shr(24).and(BigInteger.fromInt(255)).intValue())
        scaleCodecWriter.directWrite(value.shr(32).and(BigInteger.fromInt(255)).intValue())
        scaleCodecWriter.directWrite(value.shr(40).and(BigInteger.fromInt(255)).intValue())
        scaleCodecWriter.directWrite(value.shr(48).and(BigInteger.fromInt(255)).intValue())
        scaleCodecWriter.directWrite(value.shr(56).and(BigInteger.fromInt(255)).intValue())
    }

    companion object {
        val MAX_UINT64 = BigInteger.parseString("18446744073709551615")
    }
}