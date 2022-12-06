package jp.co.soramitsu.substrate_sdk.scale

actual interface ScaleReader<T> {
    actual fun read(reader: ScaleCodecReader): T
}