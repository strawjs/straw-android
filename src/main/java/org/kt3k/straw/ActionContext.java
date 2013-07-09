package org.kt3k.straw;

public class ActionContext {

	private Straw straw;
	private Boolean keepAlive = false;
	private String pluginName;
	private String actionName;
	private String argumentJson;
	private String callbackId;
	private String resultJson;
	private Boolean isSuccess = false;

	public ActionContext(String pluginName, String actionName, String argumentJson, String callbackId, Straw straw) {
		this.straw = straw;
		this.pluginName = pluginName;
		this.actionName = actionName;
		this.argumentJson = argumentJson;
		this.callbackId = callbackId;
	}

	public void exec() {
		StrawPlugin plugin = this.straw.getRegistry().getPluginByName(this.pluginName);
		String resultJson = plugin.exec(actionName, argumentJson);
		this.resultJson = resultJson;
	}

	public String getActionName() {
		return this.actionName;
	}

	public String getCallbackId() {
		return this.callbackId;
	}

	public String getResultJson() {
		return this.resultJson;
	}

	public String getArgumentJson() {
		return this.argumentJson;
	}

	public Boolean isSuccess() {
		return this.isSuccess;
	}

	public void resolve(Object obj) {
		this.isSuccess = true;

		this.straw.sendResult(new ActionResult(this));
	}

	public void reject(String errorId, String errorMessage) {
		this.isSuccess = false;

		this.straw.sendResult(new ActionResult(this));
	}

	public Boolean keepAlive() {
		return this.keepAlive;
	}

	public void keepAlive(Boolean bool) {
		this.keepAlive = bool;
	}
}
