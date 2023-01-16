package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter
import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

class ListWriter<T>(private val scaleWriter: ScaleWriter<T>) : ScaleWriter<List<T>> {

    @Throws(IOException::class)
    override fun write(scaleCodecWriter: ScaleCodecWriter, value: List<T>) {
        scaleCodecWriter.writeCompact(value.size)
        for (item in value) {
            scaleWriter.write(scaleCodecWriter, item)
        }
    }
}