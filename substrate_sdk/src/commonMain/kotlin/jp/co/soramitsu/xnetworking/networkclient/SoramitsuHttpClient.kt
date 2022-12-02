package jp.co.soramitsu.xnetworking.networkclient

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

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
