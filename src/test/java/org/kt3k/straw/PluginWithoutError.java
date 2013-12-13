package org.kt3k.straw;

import org.kt3k.straw.annotation.PluginAction;

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
