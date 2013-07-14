package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;

import java.util.HashMap;
import java.io.IOException;
import java.lang.reflect.Method;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

abstract public class StrawPlugin {

	protected WebView webView;

	protected Context context;

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
	}

	public String getName() {
		return null;
	}

	public void exec(StrawDrink drink) {
		try {

			this.invokeActionMethod(drink.getActionName(), drink.getArgumentJson(), drink);

		} catch (Exception e) {
			StrawLog.printFrameworkError("Unknown Runtime Error: action=" + drink.getActionName() + " argumentJson=" + drink.getArgumentJson());
		}
	}

	public PluginActionMetaInfo getActionInfo(String actionName) {
		return this.actionInfoMap.get(actionName);
	}

	private void invokeActionMethod(String actionName, String argumentJson, StrawDrink drink) {
		PluginActionMetaInfo metaInfo = this.getActionInfo(actionName);

		if (metaInfo == null) {
			StrawLog.printFrameworkError("No Such Plugin Action: " + drink);

			return;
		}

		Object argumentObject;

		try {

			argumentObject = StrawPlugin.createArgumentJson(argumentJson, metaInfo.getArgumentType());

		} catch (JsonParseException e) {
			StrawLog.printFrameworkError(e, "JSON Parse Error: " + drink);
			return;

		} catch (JsonMappingException e) {
			StrawLog.printFrameworkError(e, "JSON Mapping Error: " + drink);
			return;

		} catch (IOException e) {
			StrawLog.printFrameworkError(e, "IO Error When Parsing JSON: " + drink);
			return;

		}

		try {

			metaInfo.getPluginAction().invoke(this, argumentObject, drink);

		} catch (SecurityException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (security exception): " + drink);

		} catch (IllegalAccessException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (illegal access exception): " + drink);

		} catch (java.lang.reflect.InvocationTargetException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (invocation target exception): " + drink);

		} catch (Exception e) {
			StrawLog.printPluginError(e, "Runtime Error inside plugin action invocation: " + drink);

		}
	}

	private static Object createArgumentJson(String argumentJson, Class<? extends Object> type) throws JsonParseException, JsonMappingException, IOException {
		return StrawUtil.jsonToObj(argumentJson, type);
	}
}
