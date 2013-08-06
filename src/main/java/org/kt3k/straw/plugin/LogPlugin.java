package org.kt3k.straw.plugin;

import org.kt3k.straw.PluginAction;
import org.kt3k.straw.StrawDrink;
import org.kt3k.straw.StrawPlugin;

import android.util.Log;

public class LogPlugin extends StrawPlugin {

	static final String DEFAULT_SERVICE_NAME = "straw";

	@Override
	public String getName() {
		return "log";
	}

	static class LogParam {
		public String serviceName;
		public String message;

		public String getServiceName() {
			return this.serviceName != null ? this.serviceName : DEFAULT_SERVICE_NAME;
		}
	}

	@PluginAction
	public void error(LogParam param, StrawDrink drink) {
		Log.e(param.getServiceName(), param.message);
	}

	@PluginAction
	public void warn(LogParam param, StrawDrink drink) {
		Log.w(param.getServiceName(), param.message);
	}

	@PluginAction
	public void info(LogParam param, StrawDrink drink) {
		Log.i(param.getServiceName(), param.message);
	}

	@PluginAction
	public void debug(LogParam param, StrawDrink drink) {
		Log.d(param.getServiceName(), param.message);
	}

	@PluginAction
	public void verbose(LogParam param, StrawDrink drink) {
		Log.v(param.getServiceName(), param.message);
	}
}
