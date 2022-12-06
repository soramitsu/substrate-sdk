package jp.co.soramitsu.substrate_sdk.scale

expect interface ScaleReader<T> {
    fun read(reader: ScaleCodecReader): T
}