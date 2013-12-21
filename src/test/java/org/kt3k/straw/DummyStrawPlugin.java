package org.kt3k.straw;

import org.kt3k.straw.annotation.Background;
import org.kt3k.straw.annotation.EventHandler;
import org.kt3k.straw.annotation.PluginAction;
import org.kt3k.straw.annotation.RunOnUiThread;

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
	public void dummyActionSuccessWithEmptyResult(Object x, StrawDrink drink) {
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

	@PluginAction
	public void actionWithWrongParameterTypes(Object _, StrawDrink drink, String str) {
		drink.success();
	}

	@PluginAction
	public void throwError(Object _, StrawDrink drink) {
		Integer a = 0;
		a = a / a;
	}

	@PluginAction
	private void actionWithWrongAccessModifier(Object _, StrawDrink drink) {
		Integer a = 0;
		a = a / a;
	}


	@EventHandler("event1")
	public void dummyHandler1(StrawEvent e) {
		e.toString();
	}

	@Background
	@EventHandler("event2")
	public void dummyHandlerOnBackground(StrawEvent e) {
		e.toString();
	}

	@RunOnUiThread
	@EventHandler("event3")
	public void dummyHanlerOnForeground(StrawEvent e) {
		e.toString();
	}

	// to increase the branch coverage of StrawEventHandlerFactory
	@EventHandler("event4")
	public void dummyEventHandlerWithWrongParameterNumber(StrawEvent e, String dummy) {
		e.toString();
	}

	// to increase the branch coverage of StrawEventHandlerFactory
	@EventHandler("event5")
	public void dummyEventHandlerWithWrongParameterType(Straw e) {
		e.toString();
	}


	// for back pressed event test
	@EventHandler(StrawPlugin.EventType.BACK_PRESSED)
	public void onBackPressed(StrawEvent e) {
		e.getClass();
	}
}
