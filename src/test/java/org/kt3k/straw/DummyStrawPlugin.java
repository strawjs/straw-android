package org.kt3k.straw;

public class DummyStrawPlugin extends StrawPlugin {

    @Override
    public String getName() {
        return "dummy";
    };

    @PluginAction
    public ActionResult dummyAction(ActionParam param) {
        ActionResult res = new ActionResult();
        res.c = param.a;
        res.d = param.b;
        return res;
    }

    static class ActionParam {
        public String a;
        public String b;
    }

    static class ActionResult {
        public String c;
        public String d;
    }

    @PluginAction
    public ActionResult actionReturnsNull() {
        return null;
    }

    @PluginAction
    public ActionResult dummyAction3() {
        ActionResult res = new ActionResult();
        return res;
    }

    @PluginAction
    public ActionResult dummyAction4() {
        ActionResult res = new ActionResult();
        res.c = "ddd";
        return res;
    }

    public ActionResult actionWithoutAnnotation() {
        ActionResult res = new ActionResult();
        return res;
    }
}
