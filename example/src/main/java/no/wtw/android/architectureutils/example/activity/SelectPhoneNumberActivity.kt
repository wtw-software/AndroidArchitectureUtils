package no.wtw.android.architectureutils.example.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import no.wtw.android.architectureutils.util.PhoneNumberHintPicker

// Relies on com.google.android.gms:play-services-auth
class SelectPhoneNumberActivity : AppCompatActivity() {

    private lateinit var hintPicker: PhoneNumberHintPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hintPicker = PhoneNumberHintPicker(this, 1 /* REQUEST_CODE_FOR_HINT_PICKER */) { credentialId ->
            val phoneNumber = credentialId
        }
    }

    fun showHintPicker() {
        hintPicker.startForResult()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        hintPicker.onActivityResult(requestCode, resultCode, intent)
    }

}