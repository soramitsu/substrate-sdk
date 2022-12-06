package jp.co.soramitsu.substrate_sdk.scale

actual interface ScaleWriter<T> {
    actual fun write(scaleWriter: ScaleCodecWriter, value: T)
}