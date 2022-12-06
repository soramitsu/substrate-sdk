package jp.co.soramitsu.substrate_sdk.runtime.definitions.types

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter

fun ScaleCodecWriter.directWrite(byteArray: ByteArray) {
    directWrite(byteArray, 0, byteArray.size)
}