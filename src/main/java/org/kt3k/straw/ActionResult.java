package org.kt3k.straw;

public class ActionResult {

	private String resultJson;
	private Boolean isSuccess = false;
	private String callbackId;

	static final String JAVASCRIPT_SCHEME = "javascript:";
	static final String OPEN_PAREN = "(";
	static final String CLOSE_PAREN = ");";
	static final String COMMA = ",";

	public ActionResult(ActionContext context) {
		this.resultJson = context.getResultJson();
		this.isSuccess = context.isSuccess();
		this.callbackId = context.getCallbackId();
	}

	public String toJsMessage() {
		return ActionResult.JAVASCRIPT_SCHEME + this.createJsInterfaceCall();
	}

	private String createJsInterfaceCall() {
		return Straw.NATIVE_TO_JS_INTERFACE_NAME + ActionResult.OPEN_PAREN + this.createJsMessageParameters() + ActionResult.CLOSE_PAREN;
	}

	private String createJsMessageParameters() {
		return this.callbackId + ActionResult.COMMA + this.isSuccess + ActionResult.COMMA + this.resultJson;
	}
}
