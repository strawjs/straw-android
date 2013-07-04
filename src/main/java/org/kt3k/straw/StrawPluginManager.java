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

    public void loadPluginByName(String name) {
        StrawPlugin plugin = this.createPluginByName(name);

        if (plugin == null) {
            return;
        }

        String pluginName = plugin.getName();
        if (pluginName == null) {
            System.out.println("Plugin name is null: " + name);
            return;
        }

        this.plugins.put(pluginName, plugin);
    }

    public StrawPlugin getPluginByName(String name) {
        return this.plugins.get(name);
    }

    private StrawPlugin createPluginByName(String name) {
        StrawPlugin plugin = null;

        try {
            plugin = this.instantiatePluginClass(this.getClassByName(name), this.webView, this.context);

        } catch (ClassNotFoundException e) {
            System.out.println("Error creating plugin class: " + name);
            System.out.println(e);

        } catch (InstantiationException e) {
            System.out.println("Error creating plugin class: " + name);
            System.out.println(e);

        } catch (IllegalAccessException e) {
            System.out.println("Error creating plugin class: " + name);
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
}
