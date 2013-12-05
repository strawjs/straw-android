package org.kt3k.straw;

import android.webkit.WebView;

import com.squareup.otto.Bus;

public class Straw {

	private WebView webView = null;
	private JsToNativeInterface jsInterface = null;
	private StrawPluginRegistry registry;
	private Bus backKeyBus;


	public static final String JS_TO_NATIVE_INTERFACE_NAME = "JS_TO_NATIVE_INTERFACE";
	public static final String NATIVE_TO_JS_INTERFACE_NAME = "NATIVE_TO_JS_INTERFACE";

	public Straw(WebView webView) {
		this.webView = webView;

		this.registry = new StrawPluginRegistry(this.webView);
		this.setUpJsInterface();
		this.initEventBuses();
	}

	public StrawPluginRegistry getRegistry() {
		return this.registry;
	}

	public void postJsMessage(String jsMessage) {
		this.webView.post(new MessageBackThread(this.webView, jsMessage));
	}

	public static Straw insertInto(WebView webView) {
		return new Straw(webView);
	}

	public void addPlugin(String pluginCanonicalName) {
		this.registry.loadPluginByName(pluginCanonicalName);
	}

	public void addPlugins(String[] pluginCanonicalNames) {
		this.registry.loadPlugins(pluginCanonicalNames);
	}

	public void removePlugin(String pluginName) {
		this.registry.unloadPlugin(pluginName);
	}

	public void clearPlugins() {
		this.registry.unloadAllPlugins();
	}

	private void setUpJsInterface() {
		this.jsInterface = new JsToNativeInterfaceImpl(this);

		this.webView.addJavascriptInterface(this.jsInterface, JS_TO_NATIVE_INTERFACE_NAME);
	}

	/**
	 * initialize event buses
	 */
	private void initEventBuses() {
		this.backKeyBus = new Bus();
	}


	/**
	 * register for back key pressed event
	 * @param plugin
	 */
	public void registerForBackPressed(StrawPlugin plugin) {
		this.backKeyBus.register(plugin);
	}

	/**
	 * unregister for back key pressed event
	 * @param plugin
	 */
	public void unregisterForBackPressed(StrawPlugin plugin) {
		this.backKeyBus.unregister(plugin);
	}


	/**
	 * call this method inside the activity's `onBackPressed`
	 */
	public void onBackPressed() {
		// post back key events to registered handlers
		this.backKeyBus.post(new Object());
	}
}
