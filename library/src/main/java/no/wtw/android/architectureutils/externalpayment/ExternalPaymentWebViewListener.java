package no.wtw.android.architectureutils.externalpayment;

public interface ExternalPaymentWebViewListener {

    void onExternalPaymentCancelled();

    void onExternalPaymentSuccess(String paymentMethodId);

    void setIsLoading(boolean isLoading);

    String getExternalPaymentCancelledUrl();

    String getExternalPaymentSuccessUrl();

}