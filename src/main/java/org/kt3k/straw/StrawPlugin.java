package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;

abstract public class StrawPlugin {

    public String service = null;

    private WebView webView;

    private Context context;

    public StrawPlugin(WebView webView, Context context) {
        this.webView = webView;
        this.context = context;
    }

    public void exec(String action, String arguments, String callbackId) {
    }
}