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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapWebView = view.findViewById(R.id.mapWebView);

        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mapWebView.setWebViewClient(new WebViewClient());
        mapWebView.loadUrl("file:///android_asset/osm_map.html");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapWebView.destroy();
    }
}
