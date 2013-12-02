package org.kt3k.straw;

import android.webkit.WebView;
import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Method;

import com.google.gson.JsonSyntaxException;

abstract public class StrawPlugin {

	protected WebView webView;

	protected Context context;

	protected Activity activity;

	private HashMap<String, StrawPluginAction> actionInfoMap = new HashMap<String, StrawPluginAction>();


	public static class SingleStringParam {
		public String value;
	}

	public static class SingleIntegerParam {
		public Integer value;
	}


	public StrawPlugin() {
		this.checkActionMethods();
	}

	private void checkActionMethods() {
		for (StrawPluginAction action: this.createPluginActions()) {
			this.actionInfoMap.put(action.getName(), action);
		}
	}

	public List<StrawPluginAction> createPluginActions() {
		Method[] methods = this.getClass().getDeclaredMethods();

		List<StrawPluginAction> actions = new ArrayList<StrawPluginAction>();

		for (Method method: methods) {
			StrawPluginAction action = StrawPluginAction.generateMetaInfo(method, this);

			if (action != null) {
				actions.add(action);
			}
		}

		return actions;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}

	public void setContext(Context context) {
		this.context = context;

		if (context instanceof Activity) {
			this.activity = (Activity)context;
		} else {
			StrawLog.printFrameworkError("WebView.getContext() is not an Activity. A Plugin which depends on activity doesn't work.");
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

	public StrawPluginAction getActionInfo(String actionName) {
		return this.actionInfoMap.get(actionName);
	}

	private void invokeActionMethod(String actionName, String argumentJson, StrawDrink drink) {
		StrawPluginAction action = this.getActionInfo(actionName);

		if (action == null) {
			StrawLog.printFrameworkError("No Such Plugin Action: " + drink.toString());

			return;
		}

		Object argumentObject;

		try {

			argumentObject = StrawUtil.jsonToObj(argumentJson, action.getArgumentType());

		} catch (JsonSyntaxException e) {
			StrawLog.printFrameworkError(e, "JSON Parse Error: " + drink);
			return;

		}

		action.invokeActionMethod(argumentObject, drink);

	}

}
