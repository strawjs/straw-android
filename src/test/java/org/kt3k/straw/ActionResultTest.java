package org.kt3k.straw;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.webkit.WebView;
import android.app.Activity;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ActionResultTest {

	private Straw straw;

	@Before
	public void setUp() {
		WebView webView = new WebView(new Activity());
		this.straw = new Straw(webView);
	}

	@Test
	public void testConstuctor() {
		StrawDrink ac = new StrawDrink("dummy", "dummyAction", "{}", "callbackId", this.straw);
		new ActionResult(ac);
	}

	public static class TestResult {
		public String a;
		public Integer b;
	}

	@Test
	public void testToJsMessage() {
		StrawDrink ac = new StrawDrink("dummy", "dummyAction", "{}", "callbackId", this.straw);

		TestResult res = new TestResult();
		res.a = "1";
		res.b = 2;
		ac.success(res);

		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"callbackId\",true,{\"a\":\"1\",\"b\":2},false);", new ActionResult(ac).toJsMessage());
		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"callbackId\",true,{\"a\":\"1\",\"b\":2},false);", ActionResult.toJsMessage(ac));

		ac.fail("errorIdFoo", "errorMessageBar");
		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"callbackId\",false,{\"message\":\"errorMessageBar\",\"id\":\"errorIdFoo\"},false);", new ActionResult(ac).toJsMessage());
		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"callbackId\",false,{\"message\":\"errorMessageBar\",\"id\":\"errorIdFoo\"},false);", ActionResult.toJsMessage(ac));

		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"foo\",true,1234,false);", new ActionResult("foo", true, "1234").toJsMessage());
		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"bar\",false,5678,false);", new ActionResult("bar", false, "5678").toJsMessage());

	}
}
