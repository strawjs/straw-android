package org.kt3k.straw;

import java.lang.reflect.Method;

public class StrawPluginActionFactory {

	final static Boolean IS_BACKGROUND_DEFAULT = true;

	public static StrawPluginAction createPluginAction(Method method, StrawPlugin plugin) {
		if (isValidPluginAction(method)) {
			Boolean isBackground = IS_BACKGROUND_DEFAULT;

			if (hasRunOnUiThreadAnnotation(method)) {
				isBackground = false;
			}

			if (hasBackgroundAnnotation(method)) {
				isBackground = true;
			}

			return new StrawPluginAction(plugin, method, method.getName(), getArgumentTypeOfPluginAction(method), isBackground);
		}

		return null;
	}

	private static Boolean isValidPluginActionParameterTypes(Class<?>[] parameterTypes) {
		return parameterTypes.length == 2 && StrawDrink.class.isAssignableFrom(parameterTypes[1]);
	}

	private static Boolean isValidPluginAction(Method method) {
		if (hasPluginActionAnnotation(method)) {
			if (isValidPluginActionParameterTypes(method.getParameterTypes())) {
				return true;

			} else {
				// If a method with @PluginAction annotation doesn't have right signature types,
				// then alert it.
				StrawLog.printFrameworkError("Wrong Parameter Signature For Action Method: action=" + method.getName() + " for class=" + method.getClass().getCanonicalName());

			}

		}

		// If a method doesn't have @PluginAction annotation,
		// then it is not a target of plugin action validation,
		// and no error message will be displayed.
		return false;
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
		return method.getParameterTypes()[0];
	}

}
