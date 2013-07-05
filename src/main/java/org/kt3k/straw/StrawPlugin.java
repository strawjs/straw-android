package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;

import java.util.HashMap;
import java.lang.reflect.Method;

import org.codehaus.jackson.map.ObjectMapper;

abstract public class StrawPlugin {

    private WebView webView;

    private Context context;

    private HashMap<String, Method> methodMap = new HashMap<String, Method>();

    private ObjectMapper mapper = new ObjectMapper();

    public StrawPlugin() {
        Method[] methods = this.getClass().getMethods();
        for (Method m: methods) {
            this.methodMap.put(m.getName(), m);
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

    public String exec(String action, String jsonString) {

        try {

            Object result = null;

            Method targetMethod = this.methodMap.get(action);

            Integer parameterLength = this.getParameterLength(targetMethod);

            if (parameterLength == 0) {
                result = targetMethod.invoke(this);
            } else if (parameterLength == 1) {
                result = targetMethod.invoke(this, this.mapper.readValue(jsonString, this.getFirstParameterType(targetMethod)));
            }

            return this.mapper.writeValueAsString(result);

        } catch (SecurityException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + jsonString);
            System.out.println(e);
        } catch (java.io.IOException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + jsonString);
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + jsonString);
            System.out.println(e);
        } catch (java.lang.reflect.InvocationTargetException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + jsonString);
            System.out.println(e);
        }

        return null;
    }

    private static Integer getParameterLength(Method m) {
        Class<?>[] types = m.getParameterTypes();

        return types.length;
    }

    private static Class getFirstParameterType(Method m) {
        Class<?>[] types = m.getParameterTypes();

        if (types.length >= 1) {
            return types[0];
        }

        return null;
    }
}