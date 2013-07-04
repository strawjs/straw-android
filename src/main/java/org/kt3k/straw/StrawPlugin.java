package org.kt3k.straw;

import android.webkit.WebView;
import android.content.Context;

import org.json.JSONArray;

abstract public class StrawPlugin {

    private WebView webView;

    private Context context;

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
            this.getClass().getMethod(action, JSONArray.class).invoke(this, array);
        } catch (SecurityException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + array);
        } catch (IllegalAccessException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + array);
        } catch (java.lang.reflect.InvocationTargetException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + array);
        } catch (NoSuchMethodException e) {
            System.out.println("cannot execute action: " + action + ", arguments: " + array);
        }
        return null;
    }
}