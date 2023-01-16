package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class UInt32Reader : ScaleReader<Long> {

    override fun read(reader: ScaleCodecReader): Long {
        var result: Long = 0
        result += reader.readUByte().toLong()
        result += reader.readUByte().toLong() shl 8
        result += reader.readUByte().toLong() shl 2 * 8
        result += reader.readUByte().toLong() shl 3 * 8
        return result
    }
}