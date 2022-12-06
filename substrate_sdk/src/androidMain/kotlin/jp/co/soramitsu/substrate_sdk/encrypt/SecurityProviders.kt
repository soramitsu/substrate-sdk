package jp.co.soramitsu.substrate_sdk.encrypt

import net.i2p.crypto.eddsa.EdDSASecurityProvider
import java.security.Security

object SecurityProviders {

    val requireEdDSA by lazy {
        Security.addProvider(EdDSASecurityProvider())
    }

    val requireBouncyCastle by lazy {
        //Security.addProvider(BouncyCastleProvider())
    }
}
