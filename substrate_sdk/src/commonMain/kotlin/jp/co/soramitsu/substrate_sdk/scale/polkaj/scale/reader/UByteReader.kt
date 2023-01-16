package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class UByteReader : ScaleReader<Int> {
    override fun read(reader: ScaleCodecReader): Int {
        val x = reader.readByte()
        return if (x < 0) {
            256 + x.toInt()
        } else {
            x.toInt()
        }
    }
}