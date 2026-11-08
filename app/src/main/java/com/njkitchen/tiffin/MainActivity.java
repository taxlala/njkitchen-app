package com.njkitchen.tiffin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends Activity {
    private static final String HOST = "appassets.androidplatform.net";
    private static final String START = "https://" + HOST + "/assets/www/index.html#home";
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(false);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest req) {
                Uri u = req.getUrl();
                if (!HOST.equals(u.getHost())) return null;
                String path = u.getPath();
                if (path == null || !path.startsWith("/assets/")) return null;
                String asset = path.substring("/assets/".length());
                try {
                    InputStream in = getAssets().open(asset);
                    return new WebResourceResponse(mime(asset), "UTF-8", in);
                } catch (Exception e) {
                    return new WebResourceResponse("text/plain", "UTF-8", 404, "Not Found", null, null);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                return openOutside(req.getUrl());
            }
        });
        setContentView(web);
        if (savedInstanceState != null) web.restoreState(savedInstanceState);
        else web.loadUrl(START);
    }

    private static String mime(String p) {
        String f = p.toLowerCase();
        if (f.endsWith(".html")) return "text/html";
        if (f.endsWith(".js")) return "application/javascript";
        if (f.endsWith(".css")) return "text/css";
        if (f.endsWith(".jpg") || f.endsWith(".jpeg")) return "image/jpeg";
        if (f.endsWith(".png")) return "image/png";
        if (f.endsWith(".svg")) return "image/svg+xml";
        if (f.endsWith(".webp")) return "image/webp";
        if (f.endsWith(".json")) return "application/json";
        return "application/octet-stream";
    }

    // WhatsApp, Google Maps, phone, email and UPI links open in their own apps
    private boolean openOutside(Uri uri) {
        if (HOST.equals(uri.getHost())) return false;
        String sc = uri.getScheme() == null ? "" : uri.getScheme();
        try {
            Intent i;
            if (sc.equals("tel")) i = new Intent(Intent.ACTION_DIAL, uri);
            else if (sc.equals("mailto")) i = new Intent(Intent.ACTION_SENDTO, uri);
            else if (sc.equals("upi")) i = Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), "Pay with");
            else i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(this, "No app found to open this link", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }

    @Override
    public void onBackPressed() {
        String u = web.getUrl();
        if (u != null && u.contains("#") && !u.endsWith("#home")) web.evaluateJavascript("location.hash='home'", null);
        else super.onBackPressed();
    }
}
