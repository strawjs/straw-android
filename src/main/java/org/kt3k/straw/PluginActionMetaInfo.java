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

	public Boolean isValid() {
		return this.pluginAction != null;
	}

	public static PluginActionMetaInfo generateMetaInfo(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		if (isValidPluginActionParameterTypes(parameterTypes)) {
			PluginActionMetaInfo info = new PluginActionMetaInfo();

			info.setArgumentType(parameterTypes[0]);
			info.setPluginAction(method);

			return info;
		} else {
			String errorMessage = "Straw Framework Error: wrong parameter for action=" + method.getName() + " for class=" + method.getClass().getCanonicalName();
			System.out.println(errorMessage);

			return null;
		}
	}

	static Boolean isValidPluginActionParameterTypes(Class<?>[] parameterTypes) {
		return parameterTypes.length == 2 && parameterTypes[1].isAssignableFrom(StrawDrink.class);
	}
}
