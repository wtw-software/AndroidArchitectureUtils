package no.wtw.android.architectureutils.externalpayment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

public class ExternalPaymentWebViewClient extends WebViewClient {

    private boolean isLoading;

    private ExternalPaymentWebViewListener listener;

    public ExternalPaymentWebViewClient(ExternalPaymentWebViewListener listener) {
        if (listener == null)
            throw new RuntimeException("ExternalPaymentWebViewListener can not be null");
        this.listener = listener;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (!isMatch(url, listener.getExternalPaymentCancelledUrl(), listener.isExternalPaymentCancelledUrlExactMatch())
                && !isMatch(url, listener.getExternalPaymentSuccessUrl(), listener.isExternalPaymentSuccessUrlExactMatch())) {
            isLoading = true;
            listener.setIsLoading(true);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        new Handler().postDelayed(() -> {
            if (!isLoading)
                listener.setIsLoading(false);
        }, 500);
        isLoading = false;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        isLoading = false;
        listener.setIsLoading(false);
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return handleUri(request.getUrl().toString());
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return handleUri(url);
    }

    private boolean handleUri(final String url) {
        if (isMatch(url, listener.getExternalPaymentSuccessUrl(), listener.isExternalPaymentSuccessUrlExactMatch())) {
            listener.onExternalPaymentSuccess(url.replace(listener.getExternalPaymentSuccessUrl() + ":", ""));
            return true;
        } else if (isMatch(url, listener.getExternalPaymentCancelledUrl(), listener.isExternalPaymentCancelledUrlExactMatch())) {
            listener.onExternalPaymentCancelled();
            return true;
        } else {
            return listener.handleSpecialUrl(url);
        }
    }

    private boolean isMatch(String currentUrl, String destinationUrl, boolean isExactMatch) {
        return (currentUrl.equalsIgnoreCase(destinationUrl) && isExactMatch)
                || (currentUrl.toLowerCase().startsWith(destinationUrl.toLowerCase()) && !isExactMatch);
    }

}