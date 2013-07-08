package org.kt3k.straw;

public class ActionContext {

	private Straw straw;
	private Boolean keepAlive = false;
	private String pluginName;
	private String action;
	private String arguments;
	private String callbackId;
	private String resultJson;
	private Boolean isResolved = false;

	public ActionContext(String pluginName, String action, String arguments, String callbackId, Straw straw) {
		this.straw = straw;
		this.pluginName = pluginName;
		this.action = action;
		this.arguments = arguments;
		this.callbackId = callbackId;
	}

	public void exec() {
		this.straw.getRegistry().getPluginByName(this.pluginName);
	}

	public void resolve(Object obj) {
		ActionResult res = new ActionResult();
		this.straw.sendResult(res);
	}

	public void reject(String message) {
		ActionResult res = new ActionResult();
		this.straw.sendResult(res);
	}

	public void keepAlive(Boolean bool) {
		this.keepAlive = bool;
	}
}
