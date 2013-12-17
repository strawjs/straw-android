package org.kt3k.straw;

import org.kt3k.straw.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StrawEventHandlerFactory {

	final private static Boolean DEFAULT_BACKGROUND = false;

	/**
	 * create StrawEventHandler from plugin and method
	 * @param plugin
	 * @param method
	 * @return StrawEventHandler object or null if method is not handler
	 */
	static public StrawEventHandler create(StrawPlugin plugin, Method method) {

		EventHandler annotation = method.getAnnotation(EventHandler.class);

		// a valid event handler is a method which has the EventHandler annotation and the valid signature
		if (annotation != null && hasValidSignature(method)) {
			return new StrawEventHandler(plugin, annotation.value(), method, isBackgroundHandler(method));
		}

		return null;
	}

	static public List<StrawEventHandler> create(StrawPlugin plugin) {
		List<StrawEventHandler> handlers = new ArrayList<StrawEventHandler>();

		for (Method method: plugin.getClass().getMethods()) {
			StrawEventHandler handler = create(plugin, method);

			if (handler != null) {
				handlers.add(handler);
			}
		}

		return handlers;
	}

	static public List<StrawEventHandler> create(StrawPlugin[] plugins) {
		List<StrawEventHandler> handlers = new ArrayList<StrawEventHandler>();

		for (StrawPlugin plugin: plugins) {
			for (StrawEventHandler handler: create(plugin)) {
				handlers.add(handler);
			}
		}

		return handlers;
	}

	static private Boolean hasValidSignature(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		return parameterTypes.length == 1 && parameterTypes[0] == StrawEvent.class;
	}

	static Boolean isBackgroundHandler(Method method) {

		if (StrawUtil.hasAnnotation(method, Background.class)) {
			return true;
		} else if (StrawUtil.hasAnnotation(method, RunOnUiThread.class)) {
			return false;
		}

		return DEFAULT_BACKGROUND;
	}
}
