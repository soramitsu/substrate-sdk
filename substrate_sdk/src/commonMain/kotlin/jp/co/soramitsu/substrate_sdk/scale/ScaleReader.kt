package jp.co.soramitsu.substrate_sdk.scale

interface ScaleReader<T> {
    fun read(reader: ScaleCodecReader): T
}