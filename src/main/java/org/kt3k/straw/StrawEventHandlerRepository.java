package org.kt3k.straw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrawEventHandlerRepository {

	private final Map<String, List<StrawEventHandler>> store = new HashMap<String, List<StrawEventHandler>>();

	public void set(StrawEventHandler handler) {
		String eventName = handler.getEventName();

		List<StrawEventHandler> list = this.store.get(eventName);

		if (list == null) {
			list = new ArrayList<StrawEventHandler>();

			this.store.put(eventName, list);
		}

		list.add(handler);
	}

	public void set(List<StrawEventHandler> handlers) {

		for (StrawEventHandler handler: handlers) {
			this.set(handler);
		}

	}

	public List<StrawEventHandler> get(String eventName) {
		return this.store.get(eventName);
	}
}
