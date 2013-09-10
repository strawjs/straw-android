package org.kt3k.straw;

public class PluginWithoutError extends StrawPlugin {

	@Override
	public String getName() {
		return "noErrorPlugin";
	}

	@PluginAction
	public void methodAbc(Object _, StrawDrink drink) {
	}

	@PluginAction
	public void methodDef(Object _, StrawDrink drink) {
	}

}
