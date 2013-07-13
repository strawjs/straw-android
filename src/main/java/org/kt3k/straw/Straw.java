package org.kt3k.straw;

import android.webkit.WebView;

public class Straw {

	private WebView webView = null;
	private StrawNativeToJsInterface jsInterface = null;
	private StrawPluginRegistry registry;

	public static final String JS_TO_NATIVE_INTERFACE_NAME = "strawNativeInterface";
	public static final String NATIVE_TO_JS_INTERFACE_NAME = "strawJsInterface";

	public Straw(WebView webView) {
		this.webView = webView;

		this.registry = new StrawPluginRegistry(this.webView);
		this.setUpJsInterface();
	}

	public StrawPluginRegistry getRegistry() {
		return this.registry;
	}

	public void postJsMessage(String jsMessage) {
		this.webView.post(new StrawBack(this.webView, jsMessage));
	}

	private void setUpJsInterface() {
		this.jsInterface = new StrawNativeToJsInterfaceImpl(this);

		this.webView.addJavascriptInterface(this.jsInterface, JS_TO_NATIVE_INTERFACE_NAME);
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

interface StrawNativeToJsInterface {
	public void exec(String pluginName, String actionName, String argumentJson, String callbackId);
}

class StrawNativeToJsInterfaceImpl implements StrawNativeToJsInterface {
	private Straw straw;

	public StrawNativeToJsInterfaceImpl(Straw straw) {
		this.straw = straw;
	}

	public void exec(String pluginName, String actionName, String argumentJson, String callbackId) {
		new StrawDrink(pluginName, actionName, argumentJson, callbackId, this.straw).exec();
	}
}
