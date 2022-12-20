package jp.co.soramitsu.substrate_sdk.networkclient

import io.ktor.client.HttpClient
import io.ktor.http.Headers
import kotlinx.serialization.json.Json

class SoramitsuNetworkClient(
    timeout: Long = 10000,
    logging: Boolean = false,
    provider: SoramitsuNetworkClientProvider = SoramitsuNetworkClientProviderImpl()
) {
    val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    val httpClient: HttpClient = provider.provide(
        logging = logging,
        requestTimeoutMillis = timeout,
        connectTimeoutMillis = timeout,
        socketTimeoutMillis = timeout,
        json = json
    )

    companion object {
        val WEB_SOCKET_PING_INTERVAL = 30_000L
    }
}

operator fun Headers.plus(other: Headers): Headers = when {
    this.isEmpty() -> other
    other.isEmpty() -> this
    else -> Headers.build {
        appendAll(this@plus)
        appendAll(other)
    }
}
