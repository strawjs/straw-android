package org.kt3k.straw;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StrawEventHandler {

	private final StrawPlugin plugin;
	private final String eventName;
	private final Method method;
	private final Boolean isBackground;

	public StrawEventHandler(StrawPlugin plugin, String eventName, Method method, Boolean isBackground) {
		this.plugin = plugin;
		this.eventName = eventName;
		this.method = method;
		this.isBackground = isBackground;
	}

	public StrawPlugin getPlugin() {
		return plugin;
	}

	public String getEventName() {
		return eventName;
	}

	public Method getMethod() {
		return method;
	}

	/**
	 * invoke straw event handler with event
	 * @param e fired event
	 */
	public void invoke(final StrawEvent e) {
		final StrawEventHandler self = this;
		if (this.isBackground) {
			new Thread() {

				@Override
				public void run() {
					self.invokeSync(e);
				}

			}.start();
		} else {
			this.invokeSync(e);
		}
	}

	/**
	 * invoke straw event handler synchronously
	 * @param e
	 */
	private synchronized void invokeSync(StrawEvent event) {
		try {
			this.method.invoke(plugin, event);

		} catch (IllegalArgumentException e) {
			StrawLog.printFrameworkError(e, "Cannot invoke event handler (illegal argument exception): " + event.toString());

		} catch (IllegalAccessException e) {
			StrawLog.printFrameworkError(e, "Cannot invoke event handler (illegal access exception): " + event.toString());

		} catch (InvocationTargetException e) {
			StrawLog.printFrameworkError(e, "Cannot invoke event handler (invocation target exception): " + event.toString());
			StrawLog.printFrameworkError(e.getCause(), "Cannot invoke event handler (invocation target exception): " + event.toString());

		}
	}
}
