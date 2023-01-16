package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.writer

import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecWriter
import jp.co.soramitsu.substrate_sdk.scale.ScaleWriter

class LongWriter : ScaleWriter<Long> {

    override fun write(scaleCodecWriter: ScaleCodecWriter, value: Long) {
        scaleCodecWriter.directWrite(value.toByte().toInt())
        scaleCodecWriter.directWrite((value shr 8).toByte().toInt())
        scaleCodecWriter.directWrite((value shr 16).toByte().toInt())
        scaleCodecWriter.directWrite((value shr 24).toByte().toInt())
        scaleCodecWriter.directWrite((value shr 32).toByte().toInt())
        scaleCodecWriter.directWrite((value shr 40).toByte().toInt())
        scaleCodecWriter.directWrite((value shr 48).toByte().toInt())
        scaleCodecWriter.directWrite((value shr 56).toByte().toInt())
    }
}