package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class UInt16Reader : ScaleReader<Int> {

    override fun read(reader: ScaleCodecReader): Int {
        var result = 0
        result += reader.readUByte()
        result += reader.readUByte() shl 8
        return result
    }
}