package jp.co.soramitsu.substrate_sdk

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import jp.co.soramitsu.substrate_sdk.encrypt.EncryptionType
import jp.co.soramitsu.substrate_sdk.encrypt.MultiChainEncryption
import jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate.Sr25519Keypair
import jp.co.soramitsu.substrate_sdk.encrypt.keypair.substrate.SubstrateKeypairFactory
import jp.co.soramitsu.substrate_sdk.encrypt.signer
import jp.co.soramitsu.substrate_sdk.extensions.fromHex
import jp.co.soramitsu.substrate_sdk.extensions.toHexString
import jp.co.soramitsu.substrate_sdk.wsrpc.SocketService
import jp.co.soramitsu.substrate_sdk.wsrpc.executeAsync
import jp.co.soramitsu.substrate_sdk.wsrpc.mappers.pojo
import jp.co.soramitsu.substrate_sdk.wsrpc.request.DeliveryType
import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.RuntimeRequest
import jp.co.soramitsu.substrate_sdk.wsrpc.request.runtime.chain.RuntimeVersion
import kotlinx.coroutines.launch

private val socketService = SocketService()

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        AppButton(
            text = "Check Sr25519: generate keypair",
            onClick = {
                val seed = "3132333435363738393031323334353637383930313233343536373839303132".fromHex()
                val result = SubstrateKeypairFactory.generate(
                    encryptionType = EncryptionType.SR25519,
                    seed = seed
                )

                val expectedPublicKeyHex = "741c08a06f41c596608f6774259bd9043304adfa5d3eea62760bd9be97634d63"
                val expectedPrivateKeyHex = "1ec20c6cb85bf4c7423b95752b70c312e6ae9e5701ffb310f0a9019d9c041e0a"

                val isCorrectKeypair = listOf(expectedPublicKeyHex, expectedPrivateKeyHex) ==
                        listOf(result.publicKey.toHexString(), result.privateKey.toHexString())

                ToastManager.showToast(context, text = "is correct keypair: $isCorrectKeypair")
            }
        )
        AppButton(
            text = "Sign SR25519",
            onClick = {
                val message =
                    "0400340a806419d5e278172e45cb0e50da1b031795366c99ddfe0a680bd53b142c6302286bee0000002d00000003000000e143f23803ac50e8f6f8e62695d1ce9e4e1d68aa36c1cd2cfd15340213f3423ee143f23803ac50e8f6f8e62695d1ce9e4e1d68aa36c1cd2cfd15340213f3423e"
                val messageBytes = message.fromHex()

                val seed = "3132333435363738393031323334353637383930313233343536373839303132".fromHex()
                val keypair = SubstrateKeypairFactory.generate(EncryptionType.SR25519, seed) as Sr25519Keypair

                val privateKey = keypair.privateKey.toHexString()
                val publicKey = keypair.publicKey.toHexString()
                val nonce = keypair.nonce.toHexString()

                val result = signer.sign(MultiChainEncryption.Substrate(EncryptionType.SR25519), messageBytes, keypair)

                println("privateKey: $privateKey")
                println("publicKey: $publicKey")
                println("nonce: $nonce")
                println("Result: " + result.signature.toHexString())
                Toast.makeText(context, "Ready", Toast.LENGTH_SHORT).show()
            }
        )
        AppButton(
            text = "Check WebSocket connection",
            onClick = {
                Log.d("mLog", "click")
                coroutineScope.launch {
                    socketService.start("wss://ws.parachain-collator-1.c1.sora2.soramitsu.co.jp")
                    val result = socketService.executeAsync(
                        request = RuntimeRequest(
                            method = "chain_getRuntimeVersion",
                            params = listOf()
                        ),
                        mapper = pojo<RuntimeVersion>(),
                        deliveryType = DeliveryType.AT_LEAST_ONCE,
                    )
                    Log.d("mLog", "RESULT: ${result.result?.specVersion}")
                }
            }
        )
    }
}

@Composable
private fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = text
        )
    }
}