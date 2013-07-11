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
		ActionContext ac = new ActionContext("dummy", "dummyAction", "{}", "callbackId", this.straw);
		new ActionResult(ac);
	}

	@Test
	public void testToJsMessage() {
		ActionContext ac = new ActionContext("dummy", "dummyAction", "{}", "callbackId", this.straw);

		ac.success(new Object() {
			@SuppressWarnings("unused")
			public String a = "1";
			@SuppressWarnings("unused")
			public Integer b = 2;
		});

		assertEquals("javascript:strawJsInterface(\"callbackId\",true,{\"a\":\"1\",\"b\":2});", new ActionResult(ac).toJsMessage());
		assertEquals("javascript:strawJsInterface(\"callbackId\",true,{\"a\":\"1\",\"b\":2});", ActionResult.toJsMessage(ac));

		ac.fail("errorIdFoo", "errorMessageBar");
		assertEquals("javascript:strawJsInterface(\"callbackId\",false,{\"message\":\"errorMessageBar\",\"id\":\"errorIdFoo\"});", new ActionResult(ac).toJsMessage());
		assertEquals("javascript:strawJsInterface(\"callbackId\",false,{\"message\":\"errorMessageBar\",\"id\":\"errorIdFoo\"});", ActionResult.toJsMessage(ac));

		assertEquals("javascript:strawJsInterface(\"foo\",true,1234);", new ActionResult("foo", true, "1234").toJsMessage());
		assertEquals("javascript:strawJsInterface(\"bar\",false,5678);", new ActionResult("bar", false, "5678").toJsMessage());

	}
}
