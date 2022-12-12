package no.wtw.android.architectureutils.example.activity

import no.wtw.android.architectureutils.externalpayment.ExternalPaymentWebViewActivity

class WebActivity : ExternalPaymentWebViewActivity() {

    override fun getExternalPaymentUrl() =
        "https://www.vg.no"

    override fun onExternalPaymentSuccess(paymentMethodId: String) {
        finish()
    }

    override fun handleSpecialUrl(url: String) = false

}