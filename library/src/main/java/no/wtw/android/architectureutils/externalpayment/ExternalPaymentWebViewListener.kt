package no.wtw.android.architectureutils.externalpayment

interface ExternalPaymentWebViewListener {

    fun onExternalPaymentCancelled()
    fun onExternalPaymentSuccess(paymentMethodId: String)
    fun setIsLoading(isLoading: Boolean)
    fun getExternalPaymentCancelledUrl(): String
    fun getExternalPaymentSuccessUrl(): String

    /* Return true for exact match on cancel URL, or false for startsWith-match */
    fun isExternalPaymentCancelledUrlExactMatch(): Boolean
    /* Return true for exact match on success URL, or false for startsWith-match */
    fun isExternalPaymentSuccessUrlExactMatch(): Boolean

    fun handleSpecialUrl(url: String): Boolean

}