package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class UnsupportedReader<T>(
    private val message: String = "Reading an unsupported value"
) : ScaleReader<T> {

    override fun read(reader: ScaleCodecReader): T {
        throw IllegalStateException(message)
    }
}