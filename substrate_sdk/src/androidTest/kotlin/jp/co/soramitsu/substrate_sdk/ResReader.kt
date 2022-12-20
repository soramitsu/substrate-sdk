package jp.co.soramitsu.substrate_sdk

actual fun readBinaryResource(
    resourceName: String
): ByteArray {
    return ClassLoader
        .getSystemResourceAsStream(resourceName)
        .readBytes()
}