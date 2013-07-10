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

	public String exec(String actionName, String argumentJson) {

		Object result = null;

		Method targetMethod = this.methodMap.get(actionName);

		if (targetMethod == null) {
			System.out.println("No Such Plugin Action: " + actionName + ", arguments: " + argumentJson);
			return null;
		}

		Class<?>[] parameterTypes = targetMethod.getParameterTypes();

		try {

			if (parameterTypes.length == 0) {
				result = targetMethod.invoke(this);
			} else if (parameterTypes.length == 1) {
				result = targetMethod.invoke(this, StrawPlugin.jsonToObj(argumentJson, parameterTypes[0]));
			}

			return StrawPlugin.objToJson(result);

		} catch (SecurityException e) {
			System.out.println("cannot execute action: " + actionName + ", arguments: " + argumentJson);
			System.out.println(e);
		} catch (java.io.IOException e) {
			System.out.println("cannot execute action: " + actionName + ", arguments: " + argumentJson);
			System.out.println(e);
		} catch (IllegalAccessException e) {
			System.out.println("cannot execute action: " + actionName + ", arguments: " + argumentJson);
			System.out.println(e);
		} catch (java.lang.reflect.InvocationTargetException e) {
			System.out.println("cannot execute action: " + actionName + ", arguments: " + argumentJson);
			System.out.println(e);
		}

		return null;
	}

	public static String objToJson(Object value) throws JsonGenerationException, JsonMappingException, IOException {
		return StrawPlugin.objectMapper.writeValueAsString(value);
	}

	public static Object jsonToObj(String json, Class<?> type) throws JsonParseException, JsonMappingException, IOException {
		return StrawPlugin.objectMapper.readValue(json, type);
	}
}
