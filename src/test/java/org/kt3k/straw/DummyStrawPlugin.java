package org.kt3k.straw;

public class DummyStrawPlugin extends StrawPlugin {

	@Override
	public String getName() {
		return "dummy";
	};

	@PluginAction
	public void dummyAction(DummyActionParam param, StrawDrink drink) {
		DummyActionResult res = new DummyActionResult();
		res.c = param.a;
		res.d = param.b;

		drink.success(res);
	}

	public static class DummyActionParam {
		public String a;
		public String b;
	}

	public static class DummyActionResult {
		public String c;
		public String d;

		@Override
		public boolean equals(Object value) {
			return value != null && this.toString().equals(value.toString());
		}

		public String toString() {
			return "DummyActionResult(" + this.c + ", " + this.d + ")";
		}
	}

	@PluginAction
	public void dummyAction3(Object x, StrawDrink drink) {
		DummyActionResult res = new DummyActionResult();

		drink.success(res);
	}

	@PluginAction
	public void dummyAction4(Object _, StrawDrink drink) {
		DummyActionResult res = new DummyActionResult();
		res.c = "ddd";

		drink.success(res);
	}

	public void actionWithoutAnnotation(Object _, StrawDrink drink) {
		DummyActionResult res = new DummyActionResult();

		drink.success(res);
	}
}
