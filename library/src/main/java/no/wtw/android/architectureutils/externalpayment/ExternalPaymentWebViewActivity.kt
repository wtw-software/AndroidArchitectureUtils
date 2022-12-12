package no.wtw.android.architectureutils.externalpayment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.viewbinding.library.activity.viewBinding
import android.webkit.CookieManager
import android.widget.Toast
import no.wtw.android.architectureutils.databinding.ExternalPaymentWebViewActivityBinding

abstract class ExternalPaymentWebViewActivity :
    Activity(),
    ExternalPaymentWebViewListener {

    private val viewBinding by viewBinding<ExternalPaymentWebViewActivityBinding>()
    private lateinit var client: ExternalPaymentWebViewClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = ExternalPaymentWebViewClient(this)
        viewBinding.apply {
            externalPaymentWebView.settings.apply {
                domStorageEnabled = true
                databaseEnabled = true
                @SuppressLint("SetJavaScriptEnabled")
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                CookieManager.getInstance().setAcceptThirdPartyCookies(externalPaymentWebView, true)
            externalPaymentWebView.webViewClient = client
            try {
                externalPaymentWebView.loadUrl(getExternalPaymentUrl())
                externalPaymentWebView.requestFocus()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@ExternalPaymentWebViewActivity, e.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun setIsLoading(isLoading: Boolean) {
        viewBinding.progressOverlay.visibility = if (isLoading) VISIBLE else GONE
    }

    override fun onExternalPaymentCancelled() {
        setResult(RESULT_CANCELED)
        finish()
    }

    override fun getExternalPaymentCancelledUrl() = "mobillett:transcancel"
    override fun getExternalPaymentSuccessUrl() = "mobillett:transsuccess"
    override fun isExternalPaymentSuccessUrlExactMatch() = false
    override fun isExternalPaymentCancelledUrlExactMatch() = false
    protected abstract fun getExternalPaymentUrl(): String

}