package org.kt3k.straw;

public class DummyStrawPlugin extends StrawPlugin {

    @Override
    public String getName() {
        return "dummy";
    };

    @PluginAction
    public DummyActionResult dummyAction(DummyActionParam param, StrawDrink context) {
        DummyActionResult res = new DummyActionResult();
        res.c = param.a;
        res.d = param.b;
        return res;
    }

    static class DummyActionParam {
        public String a;
        public String b;
    }

    static class DummyActionResult {
        public String c;
        public String d;
    }

    @PluginAction
    public DummyActionResult actionReturnsNull() {
        return null;
    }

    @PluginAction
    public DummyActionResult dummyAction3() {
        DummyActionResult res = new DummyActionResult();
        return res;
    }

    @PluginAction
    public DummyActionResult dummyAction4() {
        DummyActionResult res = new DummyActionResult();
        res.c = "ddd";
        return res;
    }

    public DummyActionResult actionWithoutAnnotation() {
        DummyActionResult res = new DummyActionResult();
        return res;
    }
}
