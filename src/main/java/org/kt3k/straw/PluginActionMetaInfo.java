package org.kt3k.straw;

import java.lang.reflect.Method;

class PluginActionMetaInfo {

	private StrawPlugin plugin;
	private Class<?> argumentType = null;
	private Method pluginAction;
	private Boolean isBackgroundAction = true;

	private PluginActionMetaInfo(StrawPlugin plugin, Method method, Class<?> argumentType) {
		this.plugin = plugin;
		this.pluginAction = method;
		this.argumentType = argumentType;
	}

	public StrawPlugin getPluginObject() {
		return this.plugin;
	}

	public Class<?> getArgumentType() {
		return this.argumentType;
	}

	public Method getPluginAction() {
		return this.pluginAction;
	}

	public static PluginActionMetaInfo generateMetaInfo(Method method, StrawPlugin plugin) {
		if (isValidPluginAction(method)) {
			PluginActionMetaInfo info = new PluginActionMetaInfo(plugin, method, getArgumentTypeOfPluginAction(method));

			if (hasRunOnUiThreadAnnotation(method)) {
				info.setIsBackgroundAction(false);
			}

			if (hasBackgroundAnnotation(method)) {
				info.setIsBackgroundAction(true);
			}

			return info;
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

	public void setIsBackgroundAction(Boolean isBackgroundAction) {
		this.isBackgroundAction = isBackgroundAction;
	}

	public Boolean isBackgroundAction() {
		return this.isBackgroundAction;
	}

}
