package org.kt3k.straw;

public class PluginWithoutError extends StrawPlugin {

	@Override
	public String getName() {
		return "noErrorPlugin";
	}

	public void methodAbc(Object _, StrawDrink drink) {
	}

	public void methodDef(Object _, StrawDrink drink) {
	}

}
