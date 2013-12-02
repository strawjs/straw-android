package org.kt3k.straw;

import android.webkit.WebView;
import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

abstract public class StrawPlugin {

	protected WebView webView;

	protected Context context;

	protected Activity activity;


	public static class SingleStringParam {
		public String value;
	}

	public static class SingleIntegerParam {
		public Integer value;
	}


	public StrawPlugin() {
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

}
