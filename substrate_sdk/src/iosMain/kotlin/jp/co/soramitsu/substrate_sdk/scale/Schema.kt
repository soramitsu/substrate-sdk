package jp.co.soramitsu.substrate_sdk.scale

actual abstract class Schema<S : Schema<S>> : BaseSchema<S>() {

    actual override fun read(bytes: ByteArray): EncodableStruct<S> {
        TODO()
    }

    actual  override fun toByteArray(struct: EncodableStruct<S>): ByteArray {
        TODO()
    }
}
