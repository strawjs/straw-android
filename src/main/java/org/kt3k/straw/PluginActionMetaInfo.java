package org.kt3k.straw;

import java.lang.reflect.Method;

class PluginActionMetaInfo {

	private Class<?> argumentType = null;
	private Method pluginAction = null;

	private PluginActionMetaInfo() {
	}

	public Class<?> getArgumentType() {
		return this.argumentType;
	}

	public void setArgumentType(Class<?> argumentType) {
		this.argumentType = argumentType;
	}

	public Method getPluginAction() {
		return this.pluginAction;
	}

	public void setPluginAction(Method pluginAction) {
		this.pluginAction = pluginAction;
	}

	public static PluginActionMetaInfo generateMetaInfo(Method method) {
		if (isValidPluginAction(method)) {
			PluginActionMetaInfo info = new PluginActionMetaInfo();

			info.setArgumentType(getArgumentTypeOfPluginAction(method));
			info.setPluginAction(method);

			return info;
		}

		return null;
	}

	static Boolean isValidPluginActionParameterTypes(Class<?>[] parameterTypes) {
		return parameterTypes.length == 2 && parameterTypes[1].isAssignableFrom(StrawDrink.class);
	}

	static Boolean isValidPluginAction(Method method) {
		return isValidPluginActionParameterTypes(method.getParameterTypes());
	}

	static Class<?> getArgumentTypeOfPluginAction(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		return parameterTypes.length == 2 ? parameterTypes[0] : null;
	}
}
