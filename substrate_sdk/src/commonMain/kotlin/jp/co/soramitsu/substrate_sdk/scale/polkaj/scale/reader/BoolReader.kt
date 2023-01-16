package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class BoolReader : ScaleReader<Boolean> {

    override fun read(reader: ScaleCodecReader): Boolean {
        val b = reader.readByte()
        if (b.toInt() == 0) {
            return false
        }
        if (b.toInt() == 1) {
            return true
        }
        throw IllegalStateException("Not a boolean value: $b")
    }
}