package org.kt3k.straw;

import static org.kt3k.straw.ActionResult.toJsMessage;

import com.google.gson.JsonSyntaxException;

/**
 * StrawDrink represents the straw api's execution context
 * one StrawDrink for each straw API call
 * other naming candidates were: StrawCall, StrawShot, StrawContext etc
 */
public class StrawDrink {

	private Straw straw;
	private Boolean keepAlive = false;
	private String pluginName;
	private String actionName;
	private String argumentJson;
	private String callbackId;
	private String resultJson;
	private Boolean isSuccess = false;


	/**
	 * generic plugin's result class for single String value result
	 */
	public static class SingleStringValueResult {

		public String value;

		public SingleStringValueResult(String value) {
			this.value = value;
		}

	}

	/**
	 * generic plugin's result class for single Integer value result
	 */
	public static class SingleIntegerValueResult {

		public Integer value;

		public SingleIntegerValueResult(Integer value) {
			this.value = value;
		}
	}

	public StrawDrink(String pluginName, String actionName, String argumentJson, String callbackId, Straw straw) {
		this.straw = straw;
		this.pluginName = pluginName;
		this.actionName = actionName;
		this.argumentJson = argumentJson;
		this.callbackId = callbackId;
	}

	public void exec() {
		StrawPluginActionRepository repo = this.straw.getRegistry().getActionRepositoryForPluginName(this.pluginName);

		// when plugin name unavailable
		if (repo == null) {
			StrawLog.printFrameworkError("No such plugin: " + this.toString());
			return;
		}

		StrawPluginAction action = repo.get(this.actionName);

		// when plugin action name unavailable
		if (action == null) {
			StrawLog.printFrameworkError("No such plugin action: " + this.toString());

			return;
		}

		Object argumentObject;

		try {

			argumentObject = StrawUtil.jsonToObj(argumentJson, action.getArgumentType());

		} catch (JsonSyntaxException e) {
			StrawLog.printFrameworkError(e, "JSON parse error: " + this.toString());
			return;

		}

		action.invoke(argumentObject, this);

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

	/**
	 * notify Straw Framework that plugin excecution is success without any value
	 * @param value success value
	 */
	public void success() {
		this.postResult(true, new Object());
	}

	/**
	 * notify Straw Framework that plugin excecution is success with Integer value
	 * @param value success value
	 */
	public void success(Integer value) {
		this.postResult(true, new SingleIntegerValueResult(value));
	}

	/**
	 * notify Straw Framework that plugin excecution is success with String value
	 * @param value success value
	 */
	public void success(String value) {
		this.postResult(true, new SingleStringValueResult(value));
	}

	/**
	 * notify Straw Framework that plugin excecution is success with Custom object value
	 * @param value success value
	 */
	public void success(Object value) {
		this.postResult(true, value);
	}

	public void fail(String errorId, String errorMessage) {
		this.postResult(false, new ErrorResult(errorId, errorMessage));
	}

	/**
	 * post plugin result to straw object
	 * @param isSuccess
	 * @param value
	 */
	private void postResult(Boolean isSuccess, Object value) {
		this.isSuccess = isSuccess;

		try {

			this.resultJson = StrawUtil.objToJson(value);

		} catch (Throwable e) {
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
		public String code;
		public String message;

		public ErrorResult(String id, String message) {
			this.code = id;
			this.message = message;
		}
	}
}
