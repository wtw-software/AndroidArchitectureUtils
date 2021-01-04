package no.wtw.android.architectureutils.externalpayment;

public interface ExternalPaymentWebViewListener {

    void onExternalPaymentCancelled();

    void onExternalPaymentSuccess(String paymentMethodId);

    void setIsLoading(boolean isLoading);

    String getExternalPaymentCancelledUrl();

    String getExternalPaymentSuccessUrl();

    /* Return true for exact match on cancel URL, or false for startsWith-match */
    boolean isExternalPaymentCancelledUrlExactMatch();

    /* Return true for exact match on success URL, or false for startsWith-match */
    boolean isExternalPaymentSuccessUrlExactMatch();

}