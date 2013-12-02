package org.kt3k.straw;

import java.util.HashMap;

import android.webkit.WebView;

class StrawPluginRegistry {

	public static Class<? extends StrawPlugin> getClassByName(String name) throws ClassNotFoundException {
		Class<? extends StrawPlugin> c = Class.forName(name).asSubclass(StrawPlugin.class);
		return c;
	}

	private static StrawPlugin instantiatePluginClass(Class<? extends StrawPlugin> pluginClass, WebView webView) throws InstantiationException, IllegalAccessException {
		StrawPlugin plugin = (StrawPlugin)pluginClass.newInstance();

		plugin.setWebView(webView);
		plugin.setContext(webView.getContext());

		return plugin;
	}

	private HashMap<String, StrawPlugin> plugins = new HashMap<String, StrawPlugin>();
	private HashMap<String, StrawPluginActionRepository> repos = new HashMap<String, StrawPluginActionRepository>();

	private WebView webView;;

	public StrawPluginRegistry(WebView webView) {
		this.webView = webView;
	}

	private StrawPlugin createPluginByClass(Class<? extends StrawPlugin> pluginClass) {
		StrawPlugin plugin = null;

		try {
			plugin = StrawPluginRegistry.instantiatePluginClass(pluginClass, this.webView);

		} catch (InstantiationException e) {
			StrawLog.printFrameworkError(e, "cannot instantiate plugin class: class=" + pluginClass.getCanonicalName());
			return null;

		} catch (IllegalAccessException e) {
			StrawLog.printFrameworkError(e, "illegal access: class=" + pluginClass.getCanonicalName());
			return null;

		}

		return plugin;
	}

	public StrawPlugin createPluginByName(String name) {
		StrawPlugin plugin = null;

		try {
			plugin = this.createPluginByClass(StrawPluginRegistry.getClassByName(name));
		} catch (ClassNotFoundException e) {
			StrawLog.printFrameworkError(e, "class not found: class=" + name);
			return null;
		}

		return plugin;
	}

	public StrawPluginActionRepository getActionRepositoryForPluginName(String pluginName) {
		return this.repos.get(pluginName);
	}

	public void loadPlugin(StrawPlugin plugin) {
		if (plugin == null) {
			return;
		}

		String pluginName = plugin.getName();
		if (pluginName == null) {
			StrawLog.printFrameworkError("Plugin name is null");
			return;
		}

		StrawPluginActionRepository repo = new StrawPluginActionRepository();

		for(StrawPluginAction action: plugin.createPluginActions()) {
			repo.put(action.getName(), action);
		}

		this.repos.put(pluginName, repo);
	}

	public void loadPluginByClass(Class<? extends StrawPlugin> pluginClass) {
		this.loadPlugin(this.createPluginByClass(pluginClass));
	}

	public void loadPluginByName(String name) {
		this.loadPlugin(this.createPluginByName(name));
	}

	public void loadPlugins(String[] pluginNames) {
		for (String name: pluginNames) {
			this.loadPluginByName(name);
		}
	}

	public void unloadAllPlugins() {
		this.repos.clear();
	}

	public void unloadPlugin(String name) {
		this.repos.remove(name);
	}
}
