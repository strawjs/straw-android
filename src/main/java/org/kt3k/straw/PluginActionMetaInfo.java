package org.kt3k.straw;

import java.lang.reflect.Method;

class PluginActionMetaInfo {

	private Class<?> argumentType = null;

	private PluginActionMetaInfo() {
	}

	public Class<?> getArgumentType() {
		return this.argumentType;
	}

	public void setArgumentType(Class<?> argumentType) {
		this.argumentType = argumentType;
	}

	public static PluginActionMetaInfo generateMetaInfo(Method method) {
		PluginActionMetaInfo info = new PluginActionMetaInfo();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length == 2 && parameterTypes[1].isAssignableFrom(StrawDrink.class)) {
			info.setArgumentType(parameterTypes[0]);
		} else {
			String errorMessage = "Straw Framework Error: wrong parameter for action=" + method.getName() + " for class=" + method.getClass().getCanonicalName();
			System.out.println(errorMessage);
		}

		return info;
	}
}
