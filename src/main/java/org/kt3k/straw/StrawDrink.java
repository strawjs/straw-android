package org.kt3k.straw;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

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

		} catch (JsonGenerationException e) {
			String errorMessage = "Straw Framework Error: cannot generate result json: action=" + this.getActionName() + "argumentJson=" + this.getArgumentJson();
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		} catch (JsonMappingException e) {
			String errorMessage = "Straw Framework Error: cannot generate result json: action=" + this.getActionName() + "argumentJson=" + this.getArgumentJson();
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		} catch (IOException e) {
			String errorMessage = "Straw Framework Error: cannot generate result json: action=" + this.getActionName() + "argumentJson=" + this.getArgumentJson();
			System.out.println(errorMessage);
			System.out.println(e);
			e.printStackTrace();
			return;

		}

		this.straw.postJsMessage(toJsMessage(this));
	}

	public Boolean getKeepAlive() {
		return this.keepAlive;
	}

	public void setKeepAlive(Boolean bool) {
		this.keepAlive = bool;
	}

	static class ErrorResult {
		public String message;
		public String id;

		public ErrorResult(String id, String message) {
			this.message = message;
			this.id = id;
		}
	}
}
