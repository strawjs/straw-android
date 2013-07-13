package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;

import java.util.HashMap;
import java.io.IOException;
import java.lang.reflect.Method;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

abstract public class StrawPlugin {

	protected WebView webView;

	protected Context context;

	private HashMap<String, Method> methodMap = new HashMap<String, Method>();

	private static ObjectMapper objectMapper = new ObjectMapper();

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
		PluginActionMetaInfo metaInfo = PluginActionMetaInfo.generateMetaInfo(targetMethod);

		if (targetMethod == null) {
			String errorMessage = "Straw Framework Error: No Such Plugin Action: action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			return;
		}

		Object argumentObject;

		try {

			argumentObject = StrawPlugin.createArgumentJson(argumentJson, metaInfo.getArgumentType());

		} catch (JsonParseException e) {
			String errorMessage = "Straw Framework Error: json parse error: action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		} catch (JsonMappingException e) {
			String errorMessage = "Straw Framework Error: json mapping: action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		} catch (IOException e) {
			String errorMessage = "Straw Framework Error: io error when parsing argumentJson: action=" + actionName + ", argumentJson=" + argumentJson;
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
			return;

		} catch (IllegalAccessException e) {
			String errorMessage = "Straw Framework Error: cannot invoke action method (illegal access exception): action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		} catch (java.lang.reflect.InvocationTargetException e) {
			String errorMessage = "Straw Framework Error: cannot invoke action method (invocation target exception): action=" + actionName + ", argumentJson=" + argumentJson;
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;
		}

		return;
	}

	private static Object createArgumentJson(String argumentJson, Class<? extends Object> type) throws JsonParseException, JsonMappingException, IOException {
		return StrawPlugin.jsonToObj(argumentJson, type);
	}

	public static String objToJson(Object value) throws JsonGenerationException, JsonMappingException, IOException {
		return StrawPlugin.objectMapper.writeValueAsString(value);
	}

	public static Object jsonToObj(String json, Class<?> type) throws JsonParseException, JsonMappingException, IOException {
		return StrawPlugin.objectMapper.readValue(json, type);
	}
}
