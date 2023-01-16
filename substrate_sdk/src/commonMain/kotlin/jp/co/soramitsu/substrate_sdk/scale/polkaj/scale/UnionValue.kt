package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale

data class UnionValue<T>(
    val index: Int,
    val value: T
) {
    init {
        require(index >= 0) { "Index cannot be negative number: $index" }
        require(index <= 255) { "Union can have max 255 values. Index: $index" }
    }
}