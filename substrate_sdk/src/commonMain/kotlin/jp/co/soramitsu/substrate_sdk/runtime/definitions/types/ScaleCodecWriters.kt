package jp.co.soramitsu.substrate_sdk.runtime.definitions.types

import jp.co.soramitsu.substrate_sdk.scale.codecWriters.BoolWriter

object ScaleCodecWriters {
    val BOOL: BoolWriter = BoolWriter()
}