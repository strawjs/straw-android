package org.kt3k.straw;

import static org.kt3k.straw.ActionResult.toJsMessage;

public class StrawDrink {

	private Straw straw;
	private Boolean keepAlive = false;
	private String pluginName;
	private String actionName;
	private String argumentJson;
	private String callbackId;
	private String resultJson;
	private Boolean isSuccess = false;

	public StrawDrink(String pluginName, String actionName, String argumentJson, String callbackId, Straw straw) {
		this.straw = straw;
		this.pluginName = pluginName;
		this.actionName = actionName;
		this.argumentJson = argumentJson;
		this.callbackId = callbackId;
	}

	public void exec() {
		this.straw.getRegistry().getPluginByName(this.pluginName).exec(this);
	}

	public String getPluginName() {
		return this.pluginName;
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

	public Boolean getKeepAlive() {
		return this.keepAlive;
	}

	public void setKeepAlive(Boolean bool) {
		this.keepAlive = bool;
	}

	public void success(Object value) {
		this.postResult(true, value);
	}

	public void fail(String errorId, String errorMessage) {
		this.postResult(false, new ErrorResult(errorId, errorMessage));
	}

	private void postResult(Boolean isSuccess, Object value) {
		this.isSuccess = isSuccess;

		try {

			this.resultJson = StrawUtil.objToJson(value);

		} catch (Exception e) {
			StrawLog.printFrameworkError(e, "unknown error: " + this.toString());
			return;

		}

		this.straw.postJsMessage(toJsMessage(this));
	}

	@Override
	public String toString() {
		return "plugin=" + this.getPluginName() + " action=" + this.getActionName() + " argumentJson=" + this.getArgumentJson() + " callbackId=" + this.getCallbackId();
	}

	static class ErrorResult {
		public String id;
		public String message;

		public ErrorResult(String id, String message) {
			this.id = id;
			this.message = message;
		}
	}
}
