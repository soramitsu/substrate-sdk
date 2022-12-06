package jp.co.soramitsu.substrate_sdk.scale

expect interface ScaleWriter<T> {
    fun write(scaleWriter: ScaleCodecWriter, value: T)
}