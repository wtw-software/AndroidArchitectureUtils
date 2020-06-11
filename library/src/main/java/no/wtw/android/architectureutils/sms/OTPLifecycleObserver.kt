package no.wtw.android.architectureutils.sms

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.auth.api.phone.SmsRetriever

class OTPLifecycleObserver(
        private val activity: Activity,
        private val requestCode: Int,
        val callback: (sms: String) -> Unit)
    : LifecycleObserver {

    private lateinit var smsBroadcastReceiver: SmsBroadcastReceiver

    fun registerLifecycle(lifecycle: Lifecycle): OTPLifecycleObserver {
        lifecycle.addObserver(this)
        return this
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        smsBroadcastReceiver = SmsBroadcastReceiver { intent -> activity.startActivityForResult(intent, requestCode) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        activity.registerReceiver(smsBroadcastReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        activity.unregisterReceiver(smsBroadcastReceiver)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                it.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)?.let { message ->
                    if (message.length > 4)
                        callback(message)
                }
            }
        }
    }

}
