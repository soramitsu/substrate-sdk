package jp.co.soramitsu.substrate_sdk.networkclient

import io.ktor.client.engine.HttpClientEngineConfig

inline fun <reified T> HttpClientEngineConfig.configureFor(block: T.() -> Unit) {
    if (this !is T) return

    block.invoke(this)
}