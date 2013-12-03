package org.kt3k.straw;

import java.lang.reflect.Method;

class StrawPluginAction {

	final static Boolean IS_BACKGROUND_DEFAULT = true;

	private final StrawPlugin plugin;
	private final Class<?> argumentType;
	private final Method pluginAction;
	private final Boolean isBackgroundAction;
	private final String actionName;

	StrawPluginAction(StrawPlugin plugin, Method method, String actionName, Class<?> argumentType, Boolean isBackground) {
		this.plugin = plugin;
		this.pluginAction = method;
		this.actionName = actionName;
		this.argumentType = argumentType;
		this.isBackgroundAction = isBackground;
	}

	public Class<?> getArgumentType() {
		return this.argumentType;
	}

	public String getName() {
		return this.actionName;
	}

	public StrawPlugin getPlugin() {
		return this.plugin;
	}

	public void invoke(final Object argumentObject, final StrawDrink drink) {
		if (this.isBackgroundAction) {

			final StrawPluginAction self = this;

			new Thread() {

				@Override
				public void run() {
					self.invokeSync(argumentObject, drink);
				}

			}.start();

		} else {
			this.invokeSync(argumentObject, drink);

		}
	}

	public synchronized void invokeSync(Object argumentObject, StrawDrink drink) {
		try {

			this.pluginAction.invoke(this.plugin, argumentObject, drink);

		} catch (IllegalAccessException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (illegal access exception): " + drink.toString());

		} catch (java.lang.reflect.InvocationTargetException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (invocation target exception): " + drink.toString());
			StrawLog.printFrameworkError(e.getCause(), "cannot invoke action method (invocation target exception): " + drink.toString());

		}
	}
}
