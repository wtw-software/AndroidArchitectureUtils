package no.wtw.android.architectureutils.sms

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class OTPLifecycleObserver(
        private val activity: Activity,
        private val requestCode: Int,
        private val senderNumber: String? = null,
        val callback: (sms: String) -> Unit)
    : LifecycleObserver {

    private var isGooglePlayServicesAvailable = false

    init {
        isGooglePlayServicesAvailable = ConnectionResult.SUCCESS == GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity)
    }

    private lateinit var smsBroadcastReceiver: SmsBroadcastReceiver

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        smsBroadcastReceiver = SmsBroadcastReceiver { intent -> activity.startActivityForResult(intent, requestCode) }
        if (isGooglePlayServicesAvailable)
            SmsRetriever.getClient(activity).startSmsUserConsent(senderNumber)

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
