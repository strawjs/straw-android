package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;

abstract public class StrawPlugin {

    public String name = null;

    private WebView webView;

    private Context context;

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void exec(String action, String arguments, String callbackId) {
    }
}