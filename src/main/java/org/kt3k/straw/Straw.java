package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;
import android.os.Handler;

public class Straw {

    private WebView webView = null;
    private Context context = null;
    private Handler handler = null;
    private StrawPluginManager pluginManager = null;

    private StrawJavascriptInterface jsInterface = null;
    public static final String JAVASCRIPT_INTERFACE_NAME = "strawNativeInterface";

    public Straw(WebView webView, Context context, Handler handler) {
        this.webView = webView;
        this.context = context;
        this.handler = handler;
        final StrawPluginManager pluginManager = new StrawPluginManager(this.webView, this.context);

        this.jsInterface = new StrawJavascriptInterface() {
            private StrawPluginManager pluginManager;

            public void exec(String pluginName, String action, String arguments, String callbackId) {
                this.pluginManager.exec(pluginName, action, arguments, callbackId);
            }

            public void setPluginManger(StrawPluginManager pluginManager) {
                this.pluginManager = pluginManager;
            }
        };

        this.jsInterface.setPluginManger(pluginManager);
    }

    public void sendResult(StrawPluginResponse res) {
        this.handler.post(new StrawBack(this.webView, res.toJsMessage()));
    }

    private void insertStrawIntoWebView() {
        this.webView.addJavascriptInterface(this.jsInterface, JAVASCRIPT_INTERFACE_NAME);
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

interface StrawJavascriptInterface {
    public void exec(String pluginName, String action, String arguments, String callbackId);
    public void setPluginManger(StrawPluginManager pluginManager);
}
