package org.kt3k.straw;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

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

	public void success(Object obj) {
		this.isSuccess = true;
		try {
			this.resultJson = StrawPlugin.objToJson(obj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.straw.sendResult(new ActionResult(this));
	}

	public void fail(String errorId, String errorMessage) {
		this.isSuccess = false;

		ErrorResult res = new ErrorResult(errorId, errorMessage);

		try {
			this.resultJson = StrawPlugin.objToJson(res);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.straw.sendResult(new ActionResult(this));
	}

	public Boolean keepAlive() {
		return this.keepAlive;
	}

	public void keepAlive(Boolean bool) {
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
