package org.kt3k.straw;

import java.lang.reflect.Method;

class PluginActionMetaInfo {

	final static Boolean IS_BACKGROUND_DEFAULT = true;

	private StrawPlugin plugin;
	private Class<?> argumentType = null;
	private Method pluginAction;
	private Boolean isBackgroundAction = true;

	private PluginActionMetaInfo(StrawPlugin plugin, Method method, Class<?> argumentType, Boolean isBackground) {
		this.plugin = plugin;
		this.pluginAction = method;
		this.argumentType = argumentType;
		this.isBackgroundAction = isBackground;
	}

	public Class<?> getArgumentType() {
		return this.argumentType;
	}

	public void invokeActionMethod(final Object argumentObject, final StrawDrink drink) {
		if (this.isBackgroundAction) {

			final PluginActionMetaInfo self = this;

			new Thread() {

				@Override
				public void run() {
					self.invokeActionMethodSync(argumentObject, drink);
				}

			}.start();

		} else {
			this.invokeActionMethod(argumentObject, drink);

		}
	}

	public void invokeActionMethodSync(Object argumentObject, StrawDrink drink) {
		try {

			this.pluginAction.invoke(this.plugin, argumentObject, drink);

		} catch (IllegalAccessException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (illegal access exception): " + drink.toString());

		} catch (java.lang.reflect.InvocationTargetException e) {
			StrawLog.printFrameworkError(e, "cannot invoke action method (invocation target exception): " + drink.toString());
			StrawLog.printFrameworkError(e.getCause(), "cannot invoke action method (invocation target exception): " + drink.toString());

		}
	}

	public static PluginActionMetaInfo generateMetaInfo(Method method, StrawPlugin plugin) {
		if (isValidPluginAction(method)) {
			Boolean isBackground = IS_BACKGROUND_DEFAULT;

			if (hasRunOnUiThreadAnnotation(method)) {
				isBackground = false;
			}

			if (hasBackgroundAnnotation(method)) {
				isBackground = true;
			}

			return new PluginActionMetaInfo(plugin, method, getArgumentTypeOfPluginAction(method), isBackground);
		}

		return null;
	}

	private static Boolean isValidPluginActionParameterTypes(Class<?>[] parameterTypes) {
		return parameterTypes.length == 2 && parameterTypes[1].isAssignableFrom(StrawDrink.class);
	}

	private static Boolean isValidPluginAction(Method method) {
		return hasPluginActionAnnotation(method) && isValidPluginActionParameterTypes(method.getParameterTypes());
	}

	private static Boolean hasPluginActionAnnotation(Method method) {
		return method.getAnnotation(PluginAction.class) != null;
	}

	private static Boolean hasRunOnUiThreadAnnotation(Method method) {
		return method.getAnnotation(RunOnUiThread.class) != null;
	}

	private static Boolean hasBackgroundAnnotation(Method method) {
		return method.getAnnotation(Background.class) != null;
	}

	private static Class<?> getArgumentTypeOfPluginAction(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		return parameterTypes.length == 2 ? parameterTypes[0] : null;
	}

}
