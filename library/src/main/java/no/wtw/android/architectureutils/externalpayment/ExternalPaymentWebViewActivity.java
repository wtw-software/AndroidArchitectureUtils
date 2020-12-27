package no.wtw.android.architectureutils.externalpayment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import no.wtw.android.architectureutils.view.ProgressOverlayView;

@EActivity(resName = "external_payment_web_view_activity")
public abstract class ExternalPaymentWebViewActivity extends Activity implements ExternalPaymentWebViewListener {

    @ViewById(resName = "external_payment_web_view")
    protected WebView webView;
    @ViewById(resName = "progress_overlay")
    protected ProgressOverlayView progressOverlay;

    protected ExternalPaymentWebViewClient client;

    @SuppressLint("SetJavaScriptEnabled")
    @AfterViews
    protected void afterViews() {
        client = new ExternalPaymentWebViewClient(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webSettings.setDatabasePath("/data/data/" + webView.getContext().getPackageName() + "/databases/");
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        webView.setWebViewClient(client);
        try {
            webView.loadUrl(getExternalPaymentUrl());
            webView.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        if (isLoading)
            progressOverlay.setVisibility(View.VISIBLE);
        else
            progressOverlay.setVisibility(View.GONE);
    }

    @Override
    public void onExternalPaymentCancelled() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public String getExternalPaymentCancelledUrl() {
        return "mobillett:transcancel";
    }

    @Override
    public String getExternalPaymentSuccessUrl() {
        return "mobillett:transsuccess";
    }

    protected abstract String getExternalPaymentUrl() throws Exception;

}