package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;

import java.util.HashMap;
import java.lang.reflect.Method;

import org.codehaus.jackson.map.ObjectMapper;

abstract public class StrawPlugin {

    protected WebView webView;

    protected Context context;

    private HashMap<String, Method> methodMap = new HashMap<String, Method>();

    private ObjectMapper mapper = new ObjectMapper();

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

    public String exec(String action, String jsonString) {

        Object result = null;

        Method targetMethod = this.methodMap.get(action);

        if (targetMethod == null) {
            System.out.println("No Such Plugin Action: " + action + ", arguments: " + jsonString);
            return null;
        }

        Class<?>[] parameterTypes = targetMethod.getParameterTypes();

        try {

            if (parameterTypes.length == 0) {
                result = targetMethod.invoke(this);
            } else if (parameterTypes.length == 1) {
                result = targetMethod.invoke(this, this.mapper.readValue(jsonString, parameterTypes[0]));
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
}