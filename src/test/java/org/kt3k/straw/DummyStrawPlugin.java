package org.kt3k.straw;

class DummyStrawPlugin extends StrawPlugin {

    @Override
    public String getName() {
        return "dummy";
    };

    public ActionResult action(ActionParam param) {
        ActionResult res = new ActionResult();
        res.c = param.a;
        res.d = param.b;
        return res;
    }

    class ActionParam {
        public String a;
        public String b;
    }

    class ActionResult {
        public String c;
        public String d;
    }

}
