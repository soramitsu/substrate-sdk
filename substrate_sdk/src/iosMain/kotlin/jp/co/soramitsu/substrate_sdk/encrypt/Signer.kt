package jp.co.soramitsu.substrate_sdk.encrypt

import cocoapods.IrohaCrypto.EDPrivateKey
import cocoapods.IrohaCrypto.EDPublicKey
import cocoapods.IrohaCrypto.EDSignatureVerifier
import cocoapods.IrohaCrypto.EDSigner
import cocoapods.IrohaCrypto.SECPrivateKey
import cocoapods.IrohaCrypto.SECSigner
import cocoapods.IrohaCrypto.SNKeypair
import cocoapods.IrohaCrypto.SNPrivateKey
import cocoapods.IrohaCrypto.SNPublicKey
import cocoapods.IrohaCrypto.SNSignature
import cocoapods.IrohaCrypto.SNSignatureVerifier
import cocoapods.IrohaCrypto.SNSigner
import jp.co.soramitsu.substrate_sdk.common.toByteArray
import jp.co.soramitsu.substrate_sdk.common.toData
import jp.co.soramitsu.substrate_sdk.encrypt.keypair.Keypair
import jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate.Sr25519Keypair
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError

actual class Signer : BaseSigner() {

    actual override fun signSr25519(message: ByteArray, keypair: Sr25519Keypair): SignatureWrapper {
        return runCatchingNSError { errorPtr1 ->
            val privateKey = SNPrivateKey(rawData = (keypair.privateKey + keypair.nonce).toData(), error = errorPtr1)
            runCatchingNSError { errorPtr2 ->
                val publicKey = SNPublicKey(rawData = keypair.publicKey.toData(), error = errorPtr2)
                val signer = SNSigner(keypair = SNKeypair(privateKey = privateKey, publicKey = publicKey))
                runCatchingNSError { errorPtr3 ->
                    val rawResult =
                        signer.sign(originalData = message.toData(), error = errorPtr3)!!.rawData()
                    SignatureWrapper.Sr25519(rawResult.toByteArray())
                }.getOrThrow()
            }.getOrThrow()
        }.getOrThrow()
    }

    actual override fun verifySr25519(
        message: ByteArray,
        signature: ByteArray,
        publicKeyBytes: ByteArray
    ): Boolean {
        val verifier = SNSignatureVerifier()
        val snSignature = SNSignature(rawData = signature.toData(), error = null)
        val publicKey = SNPublicKey(rawData = publicKeyBytes.toData(), error = null)
        return verifier.verify(
            signature = snSignature,
            forOriginalData = message.toData(),
            usingPublicKey = publicKey
        )
    }

    actual override fun signEd25519(message: ByteArray, keypair: Keypair): SignatureWrapper {
        val privateKey = EDPrivateKey(rawData = keypair.privateKey.toData(), error = null)
        val signer = EDSigner(privateKey = privateKey)
        val rawResult = signer.sign(message.toData(), error = null)!!.rawData()
        return SignatureWrapper.Ed25519(rawResult.toByteArray())
    }

    actual override fun verifyEd25519(
        message: ByteArray,
        signature: ByteArray,
        publicKeyBytes: ByteArray
    ): Boolean {
        val verifier = EDSignatureVerifier()
        val snSignature = SNSignature(rawData = signature.toData(), error = null)
        val publicKey = EDPublicKey(rawData = publicKeyBytes.toData(), error = null)
        return verifier.verify(
            signature = snSignature,
            forOriginalData = message.toData(),
            usingPublicKey = publicKey
        )
    }

    actual override fun signEcdsa(
        message: ByteArray,
        keypair: Keypair,
        hasher: (ByteArray) -> ByteArray
    ): SignatureWrapper {
        return runCatchingNSError { errorPtr1 ->
            val privateKey = SECPrivateKey(rawData = keypair.privateKey.toData(), error = errorPtr1)
            val signer = SECSigner(privateKey = privateKey)
            val hashed = hasher(message).toData()
            runCatchingNSError { errorPtr2 ->
                val rawResult = signer.sign(hashed, error = errorPtr2)!!.rawData()
                val resultByteArray = rawResult.toByteArray()
                SignatureWrapper.Ecdsa(
                    v = byteArrayOf((resultByteArray[64] + 27).toByte()),
                    r = resultByteArray.copyOfRange(0, 32),
                    s = resultByteArray.copyOfRange(32, 64)
                )
            }.getOrThrow()
        }.getOrThrow()
    }

    private fun <T> runCatchingNSError(block: (errorPtr: CPointer<ObjCObjectVar<NSError?>>) -> T): Result<T> {
        return memScoped {   // All objects allocated in this block will be marked as free when its over, I use it for simplicity here.
            val objCError = alloc<ObjCObjectVar<NSError?>>()
            try {
                Result.success(block(objCError.ptr))
            } catch (e: Exception) {
                if (objCError.value != null) {
                    Result.failure(IllegalStateException(objCError.value?.toString()))
                } else {
                    Result.failure(e)
                }
            }
        }
    }

}