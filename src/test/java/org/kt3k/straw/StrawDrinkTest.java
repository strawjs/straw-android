package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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
	public void testFail() {
		Straw straw = mock(Straw.class);
		StrawDrink drink = new StrawDrink("a", "b", "c", "d", straw);

		drink.fail("errorId", "errorMessage");

		verify(straw).postJsMessage("javascript:" + Straw.NATIVE_TO_JS_INTERFACE_NAME + ".exec(\"d\",false,{\"id\":\"errorId\",\"message\":\"errorMessage\"},false);");
	}

}
