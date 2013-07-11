package org.kt3k.straw;

public class ActionResult {

	private String resultJson;
	private Boolean isSuccess = false;
	private String callbackId;

	static final String JAVASCRIPT_SCHEME = "javascript:";
	static final String OPEN_PAREN = "(";
	static final String CLOSE_PAREN = ")";
	static final String SEMI_COLON = ";";
	static final String COMMA = ",";
	static final String DOUBLE_QUOTE = "\"";

	public ActionResult(ActionContext context) {
		this.resultJson = context.getResultJson();
		this.isSuccess = context.isSuccess();
		this.callbackId = context.getCallbackId();
	}

	public String toJsMessage() {
		return ActionResult.JAVASCRIPT_SCHEME + this.createJsInterfaceCall();
	}

	private String createJsInterfaceCall() {
		return Straw.NATIVE_TO_JS_INTERFACE_NAME + OPEN_PAREN + this.createJsMessageParameters() + CLOSE_PAREN + SEMI_COLON;
	}

	private String createJsMessageParameters() {
		return DOUBLE_QUOTE + this.callbackId + DOUBLE_QUOTE + COMMA + this.isSuccess + COMMA + this.resultJson;
	}
}
