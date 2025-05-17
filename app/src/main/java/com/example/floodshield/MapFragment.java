package com.example.floodshield;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {

    private WebView mapWebView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapWebView = view.findViewById(R.id.mapWebView);

        // 1️⃣  Enable JavaScript
        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 2️⃣  Add JS interface
        mapWebView.addJavascriptInterface(
                new WebAppInterface(requireContext(), mapWebView),
                "AndroidBridge"
        );

        // 3️⃣  WebViewClient to inject data after page load
        mapWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // Example call to FloodChecker
                FloodChecker.checkRisk(22.58, 88.36, (risk, rain) -> {
                    String js = String.format(
                            "addOrUpdatePoint(%f,%f,'%s',%.1f);",
                            22.58, 88.36, risk, rain
                    );
                    mapWebView.post(() -> mapWebView.evaluateJavascript(js, null));
                });
            }
        });

        // 4️⃣  Load the local HTML
        mapWebView.loadUrl("file:///android_asset/osm_map.html");

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapWebView.destroy();
    }
}
