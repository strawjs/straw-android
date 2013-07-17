package org.kt3k.straw.plugin;

import org.kt3k.straw.PluginAction;
import org.kt3k.straw.StrawDrink;

public class UIPlugin extends org.kt3k.straw.StrawPlugin {

	@Override
	public String getName() {
		return "UI";
	}
	
	@PluginAction
	public void finish(Object _, StrawDrink drink) {
	}
}
