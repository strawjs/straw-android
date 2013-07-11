package org.kt3k.straw;

import android.webkit.WebView;

public class Straw {

	private WebView webView = null;
	private StrawJavascriptInterface jsInterface = null;
	private StrawPluginRegistry registry;

	public static final String JS_TO_NATIVE_INTERFACE_NAME = "strawNativeInterface";
	public static final String NATIVE_TO_JS_INTERFACE_NAME = "strawJsInterface";

	public Straw(WebView webView) {
		this.webView = webView;

		this.registry = new StrawPluginRegistry(this.webView);
		this.setUpJsInterface();
		this.insertStrawIntoWebView();
	}

	public StrawPluginRegistry getRegistry() {
		return this.registry;
	}

	public void postJsMessage(String jsMessage) {
		this.webView.post(new StrawBack(this.webView, jsMessage));
	}

	private void insertStrawIntoWebView() {
		this.webView.addJavascriptInterface(this.jsInterface, JS_TO_NATIVE_INTERFACE_NAME);
	}

	private void setUpJsInterface() {

		this.jsInterface = new StrawJavascriptInterface() {
			private Straw straw;

			public void exec(String pluginName, String action, String arguments, String callbackId) {
				ActionContext context = new ActionContext(pluginName, action, arguments, callbackId, this.straw);
				context.exec();
			}

			public void setStraw(Straw straw) {
				this.straw = straw;
			}
		};
		this.jsInterface.setStraw(this);
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
	public void setStraw(Straw straw);
}
