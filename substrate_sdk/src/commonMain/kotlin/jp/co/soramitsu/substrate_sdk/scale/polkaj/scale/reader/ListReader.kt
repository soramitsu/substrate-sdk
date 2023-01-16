package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class ListReader<T>(private val scaleReader: ScaleReader<T>) : ScaleReader<List<T>> {
    override fun read(reader: ScaleCodecReader): List<T> {
        val size = reader.readCompactInt()
        val result: MutableList<T> = ArrayList(size)
        for (i in 0 until size) {
            result.add(reader.read(scaleReader))
        }
        return result
    }
}