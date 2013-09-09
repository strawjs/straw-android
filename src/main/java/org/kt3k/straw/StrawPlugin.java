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

	private HashMap<String, PluginActionMetaInfo> actionInfoMap = new HashMap<String, PluginActionMetaInfo>();

	public StrawPlugin() {
		this.checkActionMethods(this.getClass().getDeclaredMethods());
	}

	private void checkActionMethods(Method[] methods) {
		for (Method method: methods) {
			this.checkActionMethod(method);
		}
	}

	private void checkActionMethod(Method method) {
		if (method.getName().equals("getName")) {
			return;
		}

		PluginActionMetaInfo metaInfo = PluginActionMetaInfo.generateMetaInfo(method, this);

		if (metaInfo != null) {
			this.actionInfoMap.put(method.getName(), metaInfo);
		} else {
			StrawLog.printFrameworkError("Wrong Parameter Signature For Action Method: action=" + method.getName() + " for class=" + method.getClass().getCanonicalName());
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
		this.invokeActionMethod(drink.getActionName(), drink.getArgumentJson(), drink);
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

		metaInfo.invokeActionMethod(argumentObject, drink);

	}

	private static Object createArgumentJson(String argumentJson, Class<? extends Object> type) throws JsonSyntaxException {
		return StrawUtil.jsonToObj(argumentJson, type);
	}
}
