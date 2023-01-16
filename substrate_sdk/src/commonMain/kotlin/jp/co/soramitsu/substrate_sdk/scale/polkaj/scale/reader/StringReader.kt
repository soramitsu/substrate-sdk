package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

/**
 * Read string, encoded as UTF-8 bytes
 */
class StringReader : ScaleReader<String> {
    override fun read(reader: ScaleCodecReader): String {
        return reader.readString()
    }
}