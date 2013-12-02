package org.kt3k.straw;

import java.util.HashMap;

public class StrawPluginActionRepository {

	private HashMap<String, StrawPluginAction> actionMap = new HashMap<String, StrawPluginAction>();

	public void put(String actionName, StrawPluginAction action) {
		this.actionMap.put(actionName, action);
	}


	public StrawPluginAction get(String actionName) {
		return this.actionMap.get(actionName);
	}

}
