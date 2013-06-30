package org.kt3k.straw;

import android.webkit.WebView;
import android.os.Handler;

public class Straw {

    private WebView webView = null;
    private Handler handler = null;

    public Straw(WebView webView, Handler handler) {
        this.webView = webView;
        this.handler = handler;
    }

    public void sendResult(StrawPluginResponse res) {
        this.handler.post(new StrawBack(this.webView, res.toJsMessage()));
    }

}

class StrawBack implements Runnable {

    WebView webView;
    String message;

    public StrawBack(WebView webView, String message) {
        this.webView = webView;
        this.message = message;
    }

    public void run() {
        this.webView.loadUrl(this.message);
    }

}
