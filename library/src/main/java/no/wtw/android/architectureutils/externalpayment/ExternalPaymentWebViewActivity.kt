package no.wtw.android.architectureutils.externalpayment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.viewbinding.library.activity.viewBinding
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Toast
import no.wtw.android.architectureutils.databinding.ExternalPaymentWebViewActivityBinding
import no.wtw.android.architectureutils.view.ProgressOverlayView

abstract class ExternalPaymentWebViewActivity :
    Activity(),
    ExternalPaymentWebViewListener {

    private val viewBinding by viewBinding<ExternalPaymentWebViewActivityBinding>()
    private lateinit var client: ExternalPaymentWebViewClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = ExternalPaymentWebViewClient(this)
        getWebView().settings.apply {
            domStorageEnabled = true
            databaseEnabled = true
            @SuppressLint("SetJavaScriptEnabled")
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            CookieManager.getInstance().setAcceptThirdPartyCookies(getWebView(), true)
        getWebView().webViewClient = client
        try {
            getWebView().loadUrl(getExternalPaymentUrl())
            getWebView().requestFocus()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this@ExternalPaymentWebViewActivity, e.message, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun setIsLoading(isLoading: Boolean) {
        getProgressOverlayView().visibility = if (isLoading) VISIBLE else GONE
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

    // Either use the default viewBinding, or supply your own AND override these methods
    protected open fun getWebView(): WebView = viewBinding.externalPaymentWebView
    protected open fun getProgressOverlayView(): ProgressOverlayView = viewBinding.progressOverlay

}