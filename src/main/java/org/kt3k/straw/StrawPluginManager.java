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
        StrawPlugin plugin;

        try {
            c = this.getClassByName(name);

            if (this.isStrawPlugin(c)) {
                plugin = this.instantiatePluginClass(c);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error adding plugin: " + name);
            System.out.println(e);
            return;
        } catch (InstantiationException e) {
            System.out.println("Error adding plugin: " + name);
            System.out.println(e);
            return;
        } catch (IllegalAccessException e) {
            System.out.println("Error adding plugin: " + name);
            System.out.println(e);
            return;
        }

    }

    private StrawPlugin instantiatePluginClass(Class pluginClass) throws InstantiationException, IllegalAccessException {
        StrawPlugin plugin = (StrawPlugin)pluginClass.newInstance();

        plugin.setWebView(this.webView);
        plugin.setContext(this.context);

        return plugin;
    }

    public static Class getClassByName(String name) throws ClassNotFoundException {
        Class c = Class.forName(name);
        return c;
    }

    public static Boolean isStrawPlugin(Class c) {
        return StrawPlugin.class.isAssignableFrom(c);
    }
}
