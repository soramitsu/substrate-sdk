package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader.UInt128Reader
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException

class UInt128Writer : ScaleWriter<BigInteger> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: BigInteger) {
        require(value.signum() >= 0) { "Negative numbers are not supported by Uint128" }
        val array = value.toByteArray()
        var pos = 0
        // sometimes BigInteger gives an extra zero byte in the start of the array
        if (array[0].toInt() == 0) {
            pos++
        }
        val len = array.size - pos
        if (len > UInt128Reader.SIZE_BYTES) {
            throw IllegalArgumentException("Value is to big for 128 bits. Has: ${len * 8} bits")
        }
        val encoded = ByteArray(UInt128Reader.SIZE_BYTES)

        //System.arraycopy(array, pos, encoded, encoded.size - len, len) // TODO: Return it!
        UInt128Reader.reverse(encoded)
        scaleCodecWriter.directWrite(encoded, 0, UInt128Reader.SIZE_BYTES)
    }
}