package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.UnionValue

class UnionReader<T>(private val mapping: List<ScaleReader<out T>>) : ScaleReader<UnionValue<T>> {

    constructor(vararg mapping: ScaleReader<out T>) : this(listOf<ScaleReader<out T>>(*mapping))

    override fun read(reader: ScaleCodecReader): UnionValue<T> {
        val index = reader.readUByte()
        check(mapping.size > index) { "Unknown type index: $index" }
        return UnionValue(index, mapping[index].read(reader))
    }
}