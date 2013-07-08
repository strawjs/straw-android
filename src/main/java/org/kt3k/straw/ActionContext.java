package org.kt3k.straw;

public class ActionContext {

    private Straw straw;
    private Boolean keepAlive = false;
    private String callbackId;

    public ActionContext(String callbackId, Straw straw) {
        this.straw = straw;
        this.callbackId = callbackId;
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
