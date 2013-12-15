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

		if (isValidEventHandler(method)) {
			return new StrawEventHandler(plugin, getEventName(method), method, isBackgroundHandler(method));
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
				if (handler != null) {
					handlers.add(handler);
				}
			}
		}

		return handlers;
	}

	static private Boolean isValidEventHandler(Method method) {
		if (!StrawUtil.hasAnnotation(method, EventHandler.class)) {
			return false;
		}

		if (!hasValidSignature(method)) {
			return false;
		}

		return true;
	}

	static private Boolean hasValidSignature(Method method) {
		return true;
	}

	static private String getEventName(Method method) {
		EventHandler anno = method.getAnnotation(EventHandler.class);

		if (anno != null) {
			return anno.value();
		}

		return null;
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
