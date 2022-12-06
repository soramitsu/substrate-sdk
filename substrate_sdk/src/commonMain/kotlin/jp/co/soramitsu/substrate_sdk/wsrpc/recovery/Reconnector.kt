package jp.co.soramitsu.substrate_sdk.wsrpc.recovery

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

private val DEFAULT_RECONNECT_STRATEGY =
    ExponentialReconnectStrategy(initialTime = 300L, base = 2.0)

class Reconnector(
    private val strategy: ReconnectStrategy = DEFAULT_RECONNECT_STRATEGY
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var inProgress: Deferred<*>? = null

    fun scheduleConnect(currentAttempt: Int, runnable: () -> Unit) {
        inProgress?.cancel()
        inProgress = coroutineScope.async {
            delay(strategy.getTimeForReconnect(currentAttempt))
            runnable.invoke()
        }
    }

    fun reset() {
        inProgress?.cancel()
        inProgress = null
    }
}