package org.kt3k.straw.plugin;

import org.kt3k.straw.PluginAction;
import org.kt3k.straw.StrawDrink;
import org.kt3k.straw.StrawPlugin;

public class ActivityPlugin extends StrawPlugin {

	@Override
	public String getName() {
		return "activity";
	}

	@PluginAction
	public void finish(Object _, StrawDrink drink) {
		this.activity.finish();
	}
}
