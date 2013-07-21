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
	private StrawDrink mockDrink;

	@Before
	public void setUpDummyPlugin() throws Exception {
		this.manager.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");
		this.dummyPlugin = manager.getPluginByName("dummy");
		this.mockDrink = mock(StrawDrink.class);
	}

	@Test
	public void testPluginActionExecution() throws InterruptedException {
		when(this.mockDrink.getActionName()).thenReturn("dummyAction");
		when(this.mockDrink.getArgumentJson()).thenReturn("{\"a\":\"foo\",\"b\":\"bar\"}");

		this.dummyPlugin.exec(this.mockDrink);

		DummyStrawPlugin.DummyActionResult res = new DummyStrawPlugin.DummyActionResult();
		res.c = "foo";
		res.d = "bar";
		this.dummyPlugin.thread.join();
		verify(this.mockDrink).success(res);


		when(this.mockDrink.getArgumentJson()).thenReturn("{\"a\":\"baz\",\"b\":\"baz\"}");
		this.dummyPlugin.exec(this.mockDrink);
		res.c = "baz";
		res.d = "baz";
		this.dummyPlugin.thread.join();
		verify(this.mockDrink).success(res);


		when(this.mockDrink.getActionName()).thenReturn("dummyActionSuccessWithEmptyResult");
		this.dummyPlugin.exec(this.mockDrink);
		res.c = null;
		res.d = null;
		this.dummyPlugin.thread.join();
		verify(this.mockDrink).success(res);
	}

	@Test
	public void testActionWithoutAnnotation() throws InterruptedException {
		when(this.mockDrink.getActionName()).thenReturn("dummyActionWithoutAnnotation");
		when(this.mockDrink.getArgumentJson()).thenReturn("{}");

		this.dummyPlugin.exec(this.mockDrink);
		this.dummyPlugin.thread.join();

		verify(this.mockDrink, never()).success(any());
	}

	@Test
	public void testWebViewIsNotNull() {
		assertNotNull(this.dummyPlugin.webView);
	}

	@Test
	public void testContextIsNotNull() {
		assertNotNull(this.dummyPlugin.context);
	}

	@Test
	public void testActivityIsNotNull() {
		assertNotNull(this.dummyPlugin.activity);
	}
}
