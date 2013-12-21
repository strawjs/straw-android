package org.kt3k.straw;

import android.webkit.WebView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class Straw {

	private final WebView webView;
	private final JsToNativeInterface jsInterface;
	private final StrawPluginRegistry registry;
	private Bus backKeyBus;


	public static final String JS_TO_NATIVE_INTERFACE_NAME = "JS_TO_NATIVE_INTERFACE";
	public static final String NATIVE_TO_JS_INTERFACE_NAME = "NATIVE_TO_JS_INTERFACE";

	public Straw(WebView webView) {
		this.webView = webView;

		this.registry = new StrawPluginRegistry(this.webView);

		this.jsInterface = new JsToNativeInterfaceImpl(this);

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

	/**
	 * expose native interface to webView JavaScript environment
	 */
	private void setUpJsInterface() {

		this.webView.addJavascriptInterface(this.jsInterface, JS_TO_NATIVE_INTERFACE_NAME);
	}

	/**
	 * initialize event buses
	 */
	private void initEventBuses() {
		this.backKeyBus = new Bus();

		this.backKeyBus.register(this);
	}


	/**
	 * handler root registered to otto event bus
	 * @param e
	 */
	@Subscribe
	public void rootHandler(StrawEvent e) {

		for (StrawEventHandler handler: this.registry.getHandlerRepository().get(e.getType())) {
			handler.invoke(e);
		}

	}


	/**
	 * call this method inside the activity's `onBackPressed`
	 */
	public void onBackPressed() {
		// post back key events to registered handlers
		this.backKeyBus.post(new StrawEvent(StrawPlugin.EventType.BACK_PRESSED));
	}

}
