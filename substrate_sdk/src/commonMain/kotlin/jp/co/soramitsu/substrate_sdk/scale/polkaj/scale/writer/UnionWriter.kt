package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.UnionValue

class UnionWriter<T>(mapping: List<ScaleWriter<out T>>) : ScaleWriter<UnionValue<T>> {

    private val mapping: MutableList<ScaleWriter<T>>

    init {
        this.mapping = ArrayList(mapping.size)
        for (t in mapping) {
            this.mapping.add(t as ScaleWriter<T>)
        }
    }

    constructor(vararg mapping: ScaleWriter<out T>) : this(listOf<ScaleWriter<out T>>(*mapping))

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: UnionValue<T>) {
        scaleCodecWriter.directWrite(value.index)
        val actual = value.value
        mapping[value.index].write(scaleCodecWriter, actual)
    }
}