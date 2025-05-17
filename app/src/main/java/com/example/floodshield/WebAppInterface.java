package com.example.floodshield;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class WebAppInterface {

    private final Context context;
    private final WebView  webView;

    public WebAppInterface(Context ctx, WebView wv) {
        this.context = ctx;
        this.webView  = wv;
    }

    /** Called from JS to show a toast */
    @JavascriptInterface                         // ← ADD THIS
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /** Called from JS to trigger a live flood‑risk check */
    @JavascriptInterface                         // ← ADD THIS
    public void requestRisk(double lat, double lon) {
        FloodChecker.checkRisk(lat, lon, (risk, rain) -> {
            String js = String.format(
                    "addOrUpdatePoint(%f,%f,'%s',%.1f);",
                    lat, lon, risk, rain
            );
            webView.post(() -> webView.evaluateJavascript(js, null));
        });
    }
}
