package org.kt3k.straw;

import android.webkit.WebView;
import android.app.Activity;
import android.content.Context;

import java.util.HashMap;
import java.lang.reflect.Method;

import com.google.gson.JsonSyntaxException;

abstract public class StrawPlugin {

	protected WebView webView;

	protected Context context;

	protected Activity activity;

	/**
	 * this field is for unit tests
	 */
	public Thread thread;

	private HashMap<String, PluginActionMetaInfo> actionInfoMap = new HashMap<String, PluginActionMetaInfo>();

	public StrawPlugin() {
		Method[] methods = this.getClass().getMethods();
		for (Method method: methods) {
			if (method.getAnnotation(PluginAction.class) != null) {
				PluginActionMetaInfo metaInfo = PluginActionMetaInfo.generateMetaInfo(method);

				if (metaInfo != null) {
					this.actionInfoMap.put(method.getName(), metaInfo);
				} else {
					StrawLog.printFrameworkError("Wrong Parameter Signature For Action Method: action=" + method.getName() + " for class=" + method.getClass().getCanonicalName());
				}
			}
		}
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}

	public void setContext(Context context) {
		this.context = context;

		if (context instanceof Activity) {
			this.activity = (Activity)context;
		} else {
			StrawLog.printFrameworkError("WebView.context is not an Activity. Plugins which depends on activity don't work.");
		}
	}

	/**
	 * returns straw-plugin's name
	 * @return straw-plugin's name
	 */
	abstract public String getName();

	public void exec(final StrawDrink drink) {
		final StrawPlugin self = this;

		this.thread = new Thread() {
			@Override
			public void run() {
				self.invokeActionMethod(drink.getActionName(), drink.getArgumentJson(), drink);
			}
		};

		this.thread.start();
	}

	public PluginActionMetaInfo getActionInfo(String actionName) {
		return this.actionInfoMap.get(actionName);
	}

	private void invokeActionMethod(String actionName, String argumentJson, StrawDrink drink) {
		PluginActionMetaInfo metaInfo = this.getActionInfo(actionName);

		if (metaInfo == null) {
			StrawLog.printFrameworkError("No Such Plugin Action: " + drink.toString());

			return;
		}

		Object argumentObject;

		try {

			argumentObject = StrawPlugin.createArgumentJson(argumentJson, metaInfo.getArgumentType());

		} catch (JsonSyntaxException e) {
			StrawLog.printFrameworkError(e, "JSON Parse Error: " + drink);
			return;

		}

		try {

			metaInfo.getPluginAction().invoke(this, argumentObject, drink);

		} catch (SecurityException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (security exception): " + drink.toString());

		} catch (IllegalAccessException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (illegal access exception): " + drink.toString());

		} catch (java.lang.reflect.InvocationTargetException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (invocation target exception): " + drink.toString());

		} catch (Throwable e) {
			StrawLog.printPluginError(e, "unknown error: " + drink.toString());

		}
	}

	private static Object createArgumentJson(String argumentJson, Class<? extends Object> type) throws JsonSyntaxException {
		return StrawUtil.jsonToObj(argumentJson, type);
	}
}
