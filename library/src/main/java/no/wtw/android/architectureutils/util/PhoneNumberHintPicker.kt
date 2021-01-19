package no.wtw.android.architectureutils.util

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest

class PhoneNumberHintPicker(
        private val activity: Activity,
        private val requestCode: Int,
        val callback: (credentialId: String) -> Unit) {

    fun startForResult(): PhoneNumberHintPicker {
        val hintRequest = HintRequest.Builder()
                .setHintPickerConfig(CredentialPickerConfig.Builder().setShowCancelButton(true).build())
                .setPhoneNumberIdentifierSupported(true)
                .setEmailAddressIdentifierSupported(false)
                .build()
        val intent = Credentials.getClient(activity).getHintPickerIntent(hintRequest)
        try {
            activity.startIntentSenderForResult(intent.intentSender, requestCode, null, 0, 0, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
            data?.let { intent ->
                val credential: Credential? = intent.getParcelableExtra(Credential.EXTRA_KEY)
                credential?.let { callback(it.id) }
            }
        }
    }

}