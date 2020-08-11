package no.wtw.android.architectureutils.example.activity

import android.content.Intent
import android.content.IntentSender.SendIntentException
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest

class SelectPhoneNumberActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_SIGN_IN_HINT = 1
    }

    // Relies on com.google.android.gms:play-services-auth
    fun showHintPicker() {
        val hintRequest = HintRequest.Builder()
                .setHintPickerConfig(CredentialPickerConfig.Builder().setShowCancelButton(true).build())
                .setPhoneNumberIdentifierSupported(true)
                .setEmailAddressIdentifierSupported(false)
                .build()
        val intent = Credentials.getClient(this).getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(intent.intentSender, REQUEST_SIGN_IN_HINT, null, 0, 0, 0)
        } catch (e: SendIntentException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            REQUEST_SIGN_IN_HINT -> {
                if (resultCode == RESULT_OK && intent != null) {
                    val credential: Credential? = intent.getParcelableExtra(Credential.EXTRA_KEY)
                    val phoneNumber = credential?.id ?: ""
                }
            }
        }
    }

}