package org.kt3k.straw;

import java.lang.reflect.Method;

class PluginActionMetaInfo {

	public static final Integer INVOCATION_TYPE_ZERO_PARAM = 0;
	public static final Integer INVOCATION_TYPE_ONE_PARAM_OBJ = 1;
	public static final Integer INVOCATION_TYPE_ONE_PARAM_CONTEXT = 2;
	public static final Integer INVOCATION_TYPE_TWO_PARAM_OBJ_CONTEXT = 3;

	private Integer type;
	private Class<?> argumentType = null;
	private Class<?> returnType = null;

	private PluginActionMetaInfo() {
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Class<?> getArgumentType() {
		return this.argumentType;
	}

	public void setArgumentType(Class<?> argumentType) {
		this.argumentType = argumentType;
	}

	public Class<?> getReturnType() {
		return this.returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public static PluginActionMetaInfo generateMetaInfo(Method method) {
		PluginActionMetaInfo info = new PluginActionMetaInfo();

		Class<?>[] parameterTypes = method.getParameterTypes();
		Class<?> returnType = method.getReturnType();

		if (parameterTypes.length == 0) {
			info.setType(INVOCATION_TYPE_ZERO_PARAM);
			info.setReturnType(returnType);
		} else if (parameterTypes.length == 1) {
			if (parameterTypes[0].isAssignableFrom(ActionContext.class)) {
				info.setType(INVOCATION_TYPE_ONE_PARAM_CONTEXT);
			} else {
				info.setType(INVOCATION_TYPE_ONE_PARAM_OBJ);
				info.setReturnType(returnType);
			}
		} else if (parameterTypes.length == 2 && parameterTypes[1].isAssignableFrom(ActionContext.class)) {
			info.setType(INVOCATION_TYPE_TWO_PARAM_OBJ_CONTEXT);
			info.setArgumentType(parameterTypes[0]);
		} else {
			String errorMessage = "Error wrong parameter for action: " + method.getName() + " for class: " + method.getClass().getCanonicalName();
			System.out.println(errorMessage);
		}

		return info;
	}
}
