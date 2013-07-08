package org.kt3k.straw;

public class ActionContext {

    private Straw straw;
    private Boolean keepAlive = false;
    private String callbackId;

    public ActionContext(String callbackId, Straw straw) {
        this.straw = straw;
        this.callbackId = callbackId;
    }

    public void resolve() {
        ActionResult res = new ActionResult();
        this.straw.sendResult(res);
    }

    public void reject() {
    }

    public void keepAlive(Boolean bool) {
        this.keepAlive = bool;
    }
}
