package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class BoolOptionalReader : ScaleReader<Boolean?> {

    override fun read(reader: ScaleCodecReader): Boolean? {
        val b = reader.readByte()
        if (b.toInt() == 0) {
            return null
        }
        if (b.toInt() == 1) {
            return false
        }
        if (b.toInt() == 2) {
            return true
        }
        throw IllegalStateException("Not a boolean option: $b")
    }
}