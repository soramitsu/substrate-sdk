package jp.co.soramitsu.substrate_sdk.encrypt.seed

val SeedCreatorInstance by lazy { SeedCreator() }

expect class SeedCreator() {

    fun deriveSeed(
        entropy: ByteArray,
        passphrase: String? = null
    ): ByteArray
}