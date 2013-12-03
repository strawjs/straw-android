package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.util.Printer;
import android.webkit.WebView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class StrawDrinkTest {

	@Test
	public void testConstructor() {
		new StrawDrink("a", "b", "c", "d", mock(Straw.class));
	}

	@Test
	public void testInitialValues() {
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", mock(Straw.class));

		assertEquals("a", drink.getPluginName());
		assertEquals("b", drink.getActionName());
		assertEquals("c", drink.getArgumentJson());
		assertEquals("d", drink.getCallbackId());

		assertEquals("plugin=a action=b argumentJson=c callbackId=d", drink.toString());
	}

	@Test
	public void testKeepAlive() {
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", mock(Straw.class));

		assertFalse(drink.getKeepAlive());

		drink.setKeepAlive(true);

		assertTrue(drink.getKeepAlive());
	}

	@Test
	public void testSuccess() {
		Straw straw = mock(Straw.class);
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", straw);

		drink.success(new Object());

		verify(straw).postJsMessage("javascript:" + Straw.NATIVE_TO_JS_INTERFACE_NAME + ".exec(\"d\",true,{},false);");
	}

	@Test
	public void testSuccessWithVoidParameter() {
		Straw straw = mock(Straw.class);
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", straw);

		drink.success();

		verify(straw).postJsMessage("javascript:" + Straw.NATIVE_TO_JS_INTERFACE_NAME + ".exec(\"d\",true,{},false);");
	}

	@Test
	public void testSuccessWithSingleIntegerParameter() {
		Straw straw = mock(Straw.class);
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", straw);

		drink.success(123);

		verify(straw).postJsMessage("javascript:" + Straw.NATIVE_TO_JS_INTERFACE_NAME + ".exec(\"d\",true,{\"value\":123},false);");
	}

	@Test
	public void testSuccessWithSingleStringParameter() {
		Straw straw = mock(Straw.class);
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", straw);

		drink.success("abc");

		verify(straw).postJsMessage("javascript:" + Straw.NATIVE_TO_JS_INTERFACE_NAME + ".exec(\"d\",true,{\"value\":\"abc\"},false);");
	}

	@Test
	public void testFail() {
		Straw straw = mock(Straw.class);
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", straw);

		drink.fail("errorCode", "errorMessage");

		verify(straw).postJsMessage("javascript:" + Straw.NATIVE_TO_JS_INTERFACE_NAME + ".exec(\"d\",false,{\"code\":\"errorCode\",\"message\":\"errorMessage\"},false);");
	}

	static class RecursiveParam {
		public RecursiveParam param;
	}

	@Test
	public void testStackOverflowOfResult() {
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", mock(Straw.class));

		RecursiveParam rec = new RecursiveParam();

		rec.param = rec;

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		drink.success(rec);

		verify(printer).println("Straw Framework Error: unknown error: plugin=a action=b argumentJson=c callbackId=d");
		verify(printer).println("java.lang.StackOverflowError");
	}


	@Test
	public void testExecWithNonexistentPluginName() {
		Straw straw = new Straw(mock(WebView.class));
		StrawDrink drink = new StrawDrink("nonexistent", "nop", "{}", "1", straw);

		// set up mock printer
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		drink.exec();

		verify(printer).println("Straw Framework Error: No such plugin: plugin=nonexistent action=nop argumentJson={} callbackId=1");
	}


	@Test
	public void testExecWithNonexistentActionName() {
		// set up straw
		Straw straw = new Straw(mock(WebView.class));
		straw.addPlugin("org.kt3k.straw.DummyStrawPlugin");

		// set up drink
		StrawDrink drink = new StrawDrink("dummy", "nonexistentAction", "{}", "1", straw);

		// set up mock printer
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		drink.exec();

		verify(printer).println("Straw Framework Error: No such plugin action: plugin=dummy action=nonexistentAction argumentJson={} callbackId=1");
	}


	@Test
	public void testExecWithBrokenArgumentJson() {
		// set up straw
		Straw straw = new Straw(mock(WebView.class));
		straw.addPlugin("org.kt3k.straw.DummyStrawPlugin");

		// set up drink
		StrawDrink drink = new StrawDrink("dummy", "dummyAction", "{broken json}", "1", straw);

		// set up mock printer
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		drink.exec();

		verify(printer).println("Straw Framework Error: JSON parse error: plugin=dummy action=dummyAction argumentJson={broken json} callbackId=1");
	}


	@Test
	public void testExecWithSuccessCall() {
		// set up straw
		Straw straw = new Straw(mock(WebView.class));
		straw.addPlugin("org.kt3k.straw.DummyStrawPlugin");

		// set up drink
		StrawDrink drink = new StrawDrink("dummy", "dummyAction", "{}", "1", straw);

		// set up mock printer
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		drink.exec();

		verify(printer, never()).println(anyString());

		assertTrue(drink.isSuccess());
	}


}
