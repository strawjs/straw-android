package org.kt3k.straw;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import android.webkit.WebView;
import android.app.Activity;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawPluginTest {

	private final Activity activity = new Activity();
	private final WebView webView = new WebView(activity);
	private final StrawPluginRegistry manager = new StrawPluginRegistry(webView);

	private StrawPlugin dummyPlugin;

	@Before
	public void setUpDummyPlugin() throws Exception {
		this.manager.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");
		this.dummyPlugin = manager.getPluginByName("dummy");
	}

	@Test
	public void testPluginActionExecution() {

		StrawDrink drink = mock(StrawDrink.class);
		when(drink.getActionName()).thenReturn("dummyAction");
		when(drink.getArgumentJson()).thenReturn("{\"a\":\"foo\",\"b\":\"bar\"}");
		this.dummyPlugin.exec(drink);

		DummyStrawPlugin.DummyActionResult res = new DummyStrawPlugin.DummyActionResult();
		res.c = "foo";
		res.d = "bar";

		verify(drink).success(res);
		//assertEquals("{\"c\":\"foo\",\"d\":\"bar\"}", this.dummyPlugin.exec("dummyAction", "{\"a\":\"foo\",\"b\":\"bar\"}"));
		//assertEquals("{\"c\":\"baz\",\"d\":\"baz\"}", this.dummyPlugin.exec("dummyAction", "{\"a\":\"baz\",\"b\":\"baz\"}"));

		//assertEquals("null", this.dummyPlugin.exec("actionReturnsNull", null));
		//assertEquals("{\"c\":null,\"d\":null}", this.dummyPlugin.exec("dummyAction3", null));
		//assertEquals("{\"c\":\"ddd\",\"d\":null}", this.dummyPlugin.exec("dummyAction4", null));
	}

	@Test
	public void testActionWithoutAnnotation() {
		//assertEquals(null, this.dummyPlugin.exec("actionWithoutAnnotation", null));
	}
}
