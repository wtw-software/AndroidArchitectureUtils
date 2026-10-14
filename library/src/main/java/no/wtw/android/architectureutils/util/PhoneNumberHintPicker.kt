package no.wtw.android.architectureutils.util

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity

class PhoneNumberHintPicker(
        private val activity: Activity,
        private val requestCode: Int,
        val callback: (credentialId: String) -> Unit) {

    fun startForResult(): PhoneNumberHintPicker {
        val request = GetPhoneNumberHintIntentRequest.builder().build()
        Identity.getSignInClient(activity).getPhoneNumberHintIntent(request)
                .addOnSuccessListener { pendingIntent ->
                    try {
                        activity.startIntentSenderForResult(pendingIntent.intentSender, requestCode, null, 0, 0, 0)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                .addOnFailureListener { it.printStackTrace() }
        return this
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
            data?.let { intent ->
                try {
                    callback(Identity.getSignInClient(activity).getPhoneNumberFromIntent(intent))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}
