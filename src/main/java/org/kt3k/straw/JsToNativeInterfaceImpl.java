package org.kt3k.straw;

class JsToNativeInterfaceImpl implements JsToNativeInterface {
	private Straw straw;

	public JsToNativeInterfaceImpl(Straw straw) {
		this.straw = straw;
	}

	public void exec(String pluginName, String actionName, String argumentJson, String callbackId) {
		new StrawDrink(pluginName, actionName, argumentJson, callbackId, this.straw).exec();
	}
}