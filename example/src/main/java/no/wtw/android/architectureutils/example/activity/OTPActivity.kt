package no.wtw.android.architectureutils.example.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import no.wtw.android.architectureutils.sms.OTPLifecycleObserver

class OTPActivity : AppCompatActivity() {

    companion object {
        // Make sure this doesn't collide with other request codes
        const val REQUEST_USER_CONSENT = 1
    }

    private lateinit var otpObserver: OTPLifecycleObserver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpObserver = OTPLifecycleObserver(this, REQUEST_USER_CONSENT, "2097") { sms ->
            val pinCode = sms.substring(0, 4)
        }.apply {
            lifecycle.addObserver(this)
        }
    }

    /*
    * Needs to pass the result on to the OTPObserver instance, which in turn triggers the callback
    */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        otpObserver.onActivityResult(requestCode, resultCode, data)
    }

}