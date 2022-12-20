package jp.co.soramitsu.substrate_sdk

import android.content.Context
import android.widget.Toast
import java.lang.ref.WeakReference

object ToastManager {

    private var toastRef: WeakReference<Toast?> = WeakReference(null)

    fun showToast(context: Context, text: String) {
        val oldToast = toastRef.get()
        oldToast?.cancel()
        val newToast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toastRef = WeakReference(newToast)
        newToast.show()
    }
}