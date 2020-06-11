package no.wtw.android.architectureutils.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadcastReceiver(private val listener: (intent: Intent) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            intent.extras?.let { extras ->
                (extras[SmsRetriever.EXTRA_STATUS] as Status?)?.let { status ->
                    if (CommonStatusCodes.SUCCESS == status.statusCode) {
                        extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)?.let { consentIntent ->
                            listener(consentIntent)
                        }
                    }
                }
            }
        }
    }

}