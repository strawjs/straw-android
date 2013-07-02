package org.kt3k.straw;

import java.util.List;
import java.util.HashMap;

import android.webkit.WebView;
import android.content.Context;

class StrawPluginManager {

    private WebView webView;
    private Context context;

    public StrawPluginManager(WebView webView, Context context) {
        this.webView = webView;
        this.context = context;
    }

    public void exec(String pluginName, String action, String arguments, String callback) {
    }

    public void loadPlugins(List<String> pluginNames) {
        for (String name: pluginNames) {
            this.loadPluginByName(name);
        }
    };

    private void loadPluginByName(String name) {
        Class c;

        try {
            c = this.getClassByName(name);
        } catch (ClassNotFoundException e) {
            System.out.println("Error adding plugin: " + name);
            return;
        }

        if (this.isStrawPlugin(c)) {
            try {
                this.loadPluginByClass(c);
            } catch (Exception e) {
                System.out.println("Error adding plugin: " + name);
                return;
            }
        }
    }

    private void loadPluginByClass(Class pluginClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class<?>[] types = {WebView.class, Context.class};
        Object[] args = {this.webView, this.context};
        StrawPlugin plugin = (StrawPlugin)pluginClass.getDeclaredConstructor(types).newInstance(args);
    }

    public static Class getClassByName(String name) throws ClassNotFoundException {
        Class c = Class.forName(name);
        return c;
    }

    public static Boolean isStrawPlugin(Class c) {
        return StrawPlugin.class.isAssignableFrom(c);
    }
}
