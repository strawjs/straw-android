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

	private HashMap<String, Method> methodMap = new HashMap<String, Method>();

	public StrawPlugin() {
		Method[] methods = this.getClass().getMethods();
		for (Method method: methods) {
			if (method.getAnnotation(PluginAction.class) != null) {
				this.methodMap.put(method.getName(), method);
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

	public void exec(StrawDrink context) {

		String actionName = context.getActionName();
		String argumentJson = context.getArgumentJson();

		Method targetMethod = this.methodMap.get(actionName);

		if (targetMethod == null) {
			String errorMessage = "Straw Framework Error: No Such Plugin Action: action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			return;
		}

		PluginActionMetaInfo metaInfo = PluginActionMetaInfo.generateMetaInfo(targetMethod);

		Object argumentObject;

		try {

			argumentObject = StrawPlugin.createArgumentJson(argumentJson, metaInfo.getArgumentType());

		} catch (JsonParseException e) {
			String errorMessage = "Straw Framework Error: JSON Parse Error: action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		} catch (JsonMappingException e) {
			String errorMessage = "Straw Framework Error: JSON Mapping Error: action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		} catch (IOException e) {
			String errorMessage = "Straw Framework Error: IO Error When Parsing JSON: action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		}

		try {

			targetMethod.invoke(this, argumentObject, context);

		} catch (SecurityException e) {
			String errorMessage = "Straw Framework Error: cannot invoke action method (security exception): action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			String errorMessage = "Straw Framework Error: cannot invoke action method (illegal access exception): action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();

		} catch (java.lang.reflect.InvocationTargetException e) {
			String errorMessage = "Straw Framework Error: cannot invoke action method (invocation target exception): action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();

		}
	}

	private static Object createArgumentJson(String argumentJson, Class<? extends Object> type) throws JsonParseException, JsonMappingException, IOException {
		return StrawUtil.jsonToObj(argumentJson, type);
	}
}
