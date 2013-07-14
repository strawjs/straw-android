package org.kt3k.straw;

import java.util.HashMap;

import android.webkit.WebView;

class StrawPluginRegistry {

	private WebView webView;
	private HashMap<String, StrawPlugin> plugins = new HashMap<String, StrawPlugin>();

	public StrawPluginRegistry(WebView webView) {
		this.webView = webView;
	}

	public void loadPlugins(String[] pluginNames) {
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

	public void unloadPlugin(String name) {
		this.plugins.remove(name);
	}

	public void unloadAllPlugins() {
		this.plugins.clear();
	}

	public StrawPlugin getPluginByName(String name) {
		return this.plugins.get(name);
	}

	private StrawPlugin createPluginByName(String name) {
		StrawPlugin plugin = null;

		try {
			plugin = StrawPluginRegistry.instantiatePluginClass(StrawPluginRegistry.getClassByName(name), this.webView);

		} catch (ClassNotFoundException e) {
			System.out.println("Straw Framework Error: class not found: class=" + name);
			System.out.println(e);
			e.printStackTrace();
			return null;

		} catch (InstantiationException e) {
			System.out.println("Straw Framework Error: cannot instantiate plugin class: class=" + name);
			System.out.println(e);
			e.printStackTrace();
			return null;

		} catch (IllegalAccessException e) {
			System.out.println("Straw Framework Error: illegal access: class=" + name);
			System.out.println(e);
			e.printStackTrace();
			return null;
		}

		return plugin;
	}

	private static StrawPlugin instantiatePluginClass(Class<? extends StrawPlugin> pluginClass, WebView webView) throws InstantiationException, IllegalAccessException {
		StrawPlugin plugin = (StrawPlugin)pluginClass.newInstance();

		plugin.setWebView(webView);
		plugin.setContext(webView.getContext());

		return plugin;
	}

	public static Class<? extends StrawPlugin> getClassByName(String name) throws ClassNotFoundException {
		Class<? extends StrawPlugin> c = Class.forName(name).asSubclass(StrawPlugin.class);
		return c;
	}
}
