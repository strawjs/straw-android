package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;

import java.util.HashMap;
import java.lang.reflect.Method;

import org.codehaus.jackson.map.ObjectMapper;

import org.json.JSONArray;

abstract public class StrawPlugin {

    private WebView webView;

    private Context context;

    private HashMap<String, Method> methodMap = new HashMap<String, Method>();

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

    public JSONArray exec(String action, JSONArray array, String callbackId) {
        try {
            Method targetMethod = this.methodMap.get(action);

            Class parameterType = this.getFirstParameterClass(targetMethod);

            //targetMethod.invoke(this, ObjectMapper.convertValue(parameterType))
        } catch (SecurityException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + array);
        }
        return null;
    }

    private static Class getFirstParameterClass(Method m) {
        Class<?>[] types = m.getParameterTypes();

        if (types.length > 1) {
            System.out.println("error too many parameter declared.");
            return null;
        } else if (types.length == 1) {
            return types[0];
        }

        return null;
    }
}