package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.util.Printer;

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

}
