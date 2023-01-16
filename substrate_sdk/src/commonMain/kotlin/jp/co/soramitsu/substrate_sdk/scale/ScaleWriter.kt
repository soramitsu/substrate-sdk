package jp.co.soramitsu.substrate_sdk.scale

import jp.co.soramitsu.substrate_sdk.common.exceptions.IOException

interface ScaleWriter<T> {

    @Throws(IOException::class)
    fun write(scaleCodecWriter: ScaleCodecWriter, value: T)
}