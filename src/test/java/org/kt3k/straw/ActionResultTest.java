package org.kt3k.straw;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import android.webkit.WebView;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ActionResultTest {

	private Straw straw;

	@Before
	public void setUp() {
		this.straw = new Straw(mock(WebView.class));
	}

	@Test
	public void testConstuctor() {
		StrawDrink ac = new StrawDrink("dummy", "dummyAction", "{}", "callbackId", this.straw);
		assertNotNull(new ActionResult(ac));

		assertNotNull(new ActionResult("callbackId", true, "{}"));
		assertNotNull(new ActionResult("callbackId", true, "{}", true));
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

		ac.fail("errorCodeFoo", "errorMessageBar");
		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"callbackId\",false,{\"code\":\"errorCodeFoo\",\"message\":\"errorMessageBar\"},false);", new ActionResult(ac).toJsMessage());
		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"callbackId\",false,{\"code\":\"errorCodeFoo\",\"message\":\"errorMessageBar\"},false);", ActionResult.toJsMessage(ac));

		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"foo\",true,1234,false);", new ActionResult("foo", true, "1234").toJsMessage());
		assertEquals("javascript:NATIVE_TO_JS_INTERFACE.exec(\"bar\",false,5678,false);", new ActionResult("bar", false, "5678").toJsMessage());

	}
}
