package org.kt3k.straw;

import java.util.HashMap;
import java.util.List;

import android.webkit.WebView;

class StrawPluginRegistry {

	private HashMap<String, StrawPluginActionRepository> repos = new HashMap<String, StrawPluginActionRepository>();

	private StrawEventHandlerRepository handlerRepository = new StrawEventHandlerRepository();

	private WebView webView;

	public StrawPluginRegistry(WebView webView) {
		this.webView = webView;
	}

	private StrawPlugin createPluginByClass(Class<? extends StrawPlugin> pluginClass) {
		StrawPlugin plugin = null;

		try {
			plugin = StrawPluginRegistry.instantiatePluginClass(pluginClass, this.webView);

		} catch (InstantiationException e) {
			StrawLog.printFrameworkError(e, "Cannot instantiate plugin class: class=" + pluginClass.getCanonicalName());
			return null;

		} catch (IllegalAccessException e) {
			StrawLog.printFrameworkError(e, "Illegal access: class=" + pluginClass.getCanonicalName());
			return null;

		}

		return plugin;
	}

	public StrawPlugin createPluginByName(String name) {
		StrawPlugin plugin = null;

		try {
			plugin = this.createPluginByClass(StrawPluginRegistry.getClassByName(name));
		} catch (ClassNotFoundException e) {
			StrawLog.printFrameworkError(e, "Class not found: class=" + name);
			return null;
		}

		return plugin;
	}

	public StrawPluginActionRepository getActionRepositoryForPluginName(String pluginName) {
		return this.repos.get(pluginName);
	}

	public StrawEventHandlerRepository getHandlerRepository() {
		return this.handlerRepository;
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

		// extract plugin action
		for(StrawPluginAction action: plugin.createPluginActions()) {
			// put StrawPluginAction into repository
			repo.put(action.getName(), action);
		}

		this.repos.put(pluginName, repo);

		// extract plugin's event handlers
		List<StrawEventHandler> handlers = StrawEventHandlerFactory.create(plugin);

		// store them into repository
		this.handlerRepository.store(handlers);
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


	public static Class<? extends StrawPlugin> getClassByName(String name) throws ClassNotFoundException {
		return Class.forName(name).asSubclass(StrawPlugin.class);
	}

	private static StrawPlugin instantiatePluginClass(Class<? extends StrawPlugin> pluginClass, WebView webView) throws InstantiationException, IllegalAccessException {
		StrawPlugin plugin = (StrawPlugin)pluginClass.newInstance();

		plugin.setWebView(webView);
		plugin.setContext(webView.getContext());

		return plugin;
	}

}
