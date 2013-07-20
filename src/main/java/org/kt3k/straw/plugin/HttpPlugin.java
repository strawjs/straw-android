package org.kt3k.straw.plugin;

import org.kt3k.straw.StrawDrink;
import org.kt3k.straw.StrawPlugin;
import org.kt3k.straw.PluginAction;

public class HttpPlugin extends StrawPlugin {

	@Override
	public String getName() {
		return "http";
	}
	
	public static class HttpParam {
		public String url;
		public String data;
	}

	@PluginAction
	public void get(HttpParam param, StrawDrink drink) {
	}

	@PluginAction
	public void post(HttpParam param, StrawDrink drink) {
	}
}
