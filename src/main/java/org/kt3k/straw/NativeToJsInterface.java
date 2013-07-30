package org.kt3k.straw;

interface NativeToJsInterface {
	public void exec(String pluginName, String actionName, String argumentJson, String callbackId);
}