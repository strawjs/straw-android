package org.kt3k.straw;

interface JsToNativeInterface {
	public void exec(String pluginName, String actionName, String argumentJson, String callbackId);
}