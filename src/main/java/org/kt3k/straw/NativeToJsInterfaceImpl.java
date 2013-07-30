package org.kt3k.straw;

class NativeToJsInterfaceImpl implements NativeToJsInterface {
	private Straw straw;

	public NativeToJsInterfaceImpl(Straw straw) {
		this.straw = straw;
	}

	public void exec(String pluginName, String actionName, String argumentJson, String callbackId) {
		new StrawDrink(pluginName, actionName, argumentJson, callbackId, this.straw).exec();
	}
}