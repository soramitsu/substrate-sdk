package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.CompactMode

class CompactUIntReader : ScaleReader<Int> {
    /**
     *
     * @param reader reader with the encoded data
     * @return integer value
     * @throws UnsupportedOperationException if the value is encoded with more than four bytes (use [CompactBigIntReader])
     */
    override fun read(reader: ScaleCodecReader): Int {
        val i = reader.readUByte()
        val mode = CompactMode.byValue((i and 3).toByte())
        if (mode == CompactMode.SINGLE) {
            return i shr 2
        }
        if (mode == CompactMode.TWO) {
            return ((i shr 2)
                    + (reader.readUByte() shl 6))
        }
        if (mode == CompactMode.FOUR) {
            return (i shr 2) +
                    (reader.readUByte() shl 6) +
                    (reader.readUByte() shl 6 + 8) +
                    (reader.readUByte() shl 6 + 2 * 8)
        }
        throw UnsupportedOperationException("Mode $mode is not implemented")
    }
}