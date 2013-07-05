package org.kt3k.straw;

public class DummyStrawPlugin extends StrawPlugin {

    @Override
    public String getName() {
        return "dummy";
    };

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

    public ActionResult dummyAction2() {
        return null;
    }

    public ActionResult dummyAction3() {
        ActionResult res = new ActionResult();
        res.c = "ddd";
        return res;
    }

}
