package org.kt3k.straw;

import java.util.List;
import java.util.HashMap;

import android.webkit.WebView;
import android.content.Context;

class StrawPluginManager {

    private WebView webView;
    private Context context;

    private HashMap<String, StrawPlugin> plugins = new HashMap<String, StrawPlugin>();

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
        StrawPlugin plugin = this.getPluginByName(name);

        if (plugin == null) {
            return;
        }

        if (plugin.name == null || plugin.name == "") {
            System.out.println("Plugin name is empty: " + name);
            return;
        }

        this.plugins.put(plugin.name, plugin);
    }

    private StrawPlugin getPluginByName(String name) {
        Class c;
        StrawPlugin plugin = null;

        try {
            c = this.getClassByName(name);

            if (this.isStrawPlugin(c)) {
                plugin = this.instantiatePluginClass(c, this.webView, this.context);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error adding plugin: " + name);
            System.out.println(e);
        } catch (InstantiationException e) {
            System.out.println("Error adding plugin: " + name);
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println("Error adding plugin: " + name);
            System.out.println(e);
        }

        return plugin;
    }

    private static StrawPlugin instantiatePluginClass(Class pluginClass, WebView webView, Context context) throws InstantiationException, IllegalAccessException {
        StrawPlugin plugin = (StrawPlugin)pluginClass.newInstance();

        plugin.setWebView(webView);
        plugin.setContext(context);

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
