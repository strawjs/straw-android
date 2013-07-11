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
		this(context.getCallbackId(), context.isSuccess(), context.getResultJson());
	}

	public ActionResult(String callbackId, Boolean isSuccess, String resultJson) {
		this.callbackId = callbackId;
		this.isSuccess = isSuccess;
		this.resultJson = resultJson;
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

	public static String toJsMessage(ActionContext context) {
		return new ActionResult(context).toJsMessage();
	}
}
