package org.kt3k.straw;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import android.util.Printer;
import android.webkit.WebView;
import android.app.Activity;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawPluginTest {

	private final Activity activity = new Activity();
	private final WebView webView = new WebView(activity);
	private final StrawPluginRegistry registry = new StrawPluginRegistry(webView);

	private StrawPlugin dummyPlugin;
	private StrawDrink mockDrink;

	@Before
	public void setUpDummyPlugin() throws Exception {
		this.registry.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");
		this.dummyPlugin = registry.getPluginByName("dummy");
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
		verify(this.mockDrink, timeout(1000)).success(res);


		when(this.mockDrink.getArgumentJson()).thenReturn("{\"a\":\"baz\",\"b\":\"baz\"}");
		this.dummyPlugin.exec(this.mockDrink);
		res.c = "baz";
		res.d = "baz";
		verify(this.mockDrink, timeout(1000)).success(res);


		when(this.mockDrink.getActionName()).thenReturn("dummyActionSuccessWithEmptyResult");
		this.dummyPlugin.exec(this.mockDrink);
		res.c = null;
		res.d = null;
		verify(this.mockDrink, timeout(1000)).success(res);
	}

	@Test
	public void testActionWithoutAnnotation() throws InterruptedException {
		when(this.mockDrink.getActionName()).thenReturn("dummyActionWithoutAnnotation");
		when(this.mockDrink.getArgumentJson()).thenReturn("{}");

		this.dummyPlugin.exec(this.mockDrink);

		verify(this.mockDrink, timeout(1000).never()).success(any());
	}

	@Test
	public void testActionWithWrongParameterTypes() throws InterruptedException {
		when(this.mockDrink.getActionName()).thenReturn("dummyActionWithWrongParameterTypes");
		when(this.mockDrink.getArgumentJson()).thenReturn("{}");

		this.dummyPlugin.exec(this.mockDrink);

		verify(this.mockDrink, timeout(1000).never()).success(any());
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

	@Test
	public void testJsonParseErrorWhenExec() {
		when(this.mockDrink.getActionName()).thenReturn("dummyAction");
		when(this.mockDrink.getArgumentJson()).thenReturn("ABC");
		when(this.mockDrink.toString()).thenReturn("baz");

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		this.dummyPlugin.exec(this.mockDrink);

		verify(printer, timeout(1000)).println("Straw Framework Error: JSON Parse Error: baz");
	}

	@Test
	public void testErrorInsidePluginAction() {
		when(this.mockDrink.getActionName()).thenReturn("throwError");
		when(this.mockDrink.getArgumentJson()).thenReturn("{}");
		when(this.mockDrink.toString()).thenReturn("baz");

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		this.dummyPlugin.exec(this.mockDrink);

		verify(printer, timeout(1000).times(2)).println("Straw Framework Error: cannot invoke action method (invocation target exception): baz");
		verify(printer, timeout(1000)).println("java.lang.reflect.InvocationTargetException");
		verify(printer, timeout(1000)).println("java.lang.ArithmeticException: / by zero");
	}

	@Test
	public void testActionWithWrongAccessModifier() {
		when(this.mockDrink.getActionName()).thenReturn("actionWithWrongAccessModifier");
		when(this.mockDrink.getArgumentJson()).thenReturn("{}");
		when(this.mockDrink.toString()).thenReturn("baz");

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		this.dummyPlugin.exec(this.mockDrink);

		verify(printer, timeout(1000)).println("Straw Framework Error: cannot invoke action method (illegal access exception): baz");
		verify(printer, timeout(1000)).println("java.lang.IllegalAccessException: Class org.kt3k.straw.PluginActionMetaInfo can not access a member of class org.kt3k.straw.DummyStrawPlugin with modifiers \"private\"");
	}
}
