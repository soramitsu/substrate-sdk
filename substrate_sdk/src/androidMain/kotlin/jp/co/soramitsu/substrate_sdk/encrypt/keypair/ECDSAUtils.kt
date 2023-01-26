package jp.co.soramitsu.substrate_sdk.encrypt.keypair

import com.ionspin.kotlin.bignum.integer.BigInteger
import jp.co.soramitsu.substrate_sdk.extensions.toHexString
import org.web3j.crypto.Sign

actual fun derivePublicKeyEcdsa(privateKeyOrSeed: ByteArray): ByteArray {
    val privateKeyInt = BigInteger.parseString(privateKeyOrSeed.toHexString(), 16)
    return compressedPublicKeyFromPrivateEcdsa(privateKeyInt)
}

private fun compressedPublicKeyFromPrivateEcdsa(privKey: BigInteger): ByteArray {
    val point = Sign.publicPointFromPrivate(privKey.toJavaBigInteger())
    return point.getEncoded(true)
}

private fun BigInteger.toJavaBigInteger(): java.math.BigInteger {
    return java.math.BigInteger(toByteArray())
}
