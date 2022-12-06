package jp.co.soramitsu.substrate_sdk.networkclient

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

actual fun HttpClientEngineConfig.configure() {
    configureFor<DarwinClientEngineConfig> {
        /* empty */
    }
}