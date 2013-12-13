package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kt3k.straw.annotation.PluginAction;
import org.kt3k.straw.annotation.RunOnUiThread;
import org.robolectric.RobolectricTestRunner;

import android.util.Printer;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

class StrawPluginFixture extends StrawPlugin {

	@Override
	public String getName() {
		return "dummy";
	}

	@PluginAction
	public void action(Object _, StrawDrink drink) {
		drink.success();
	}

	@PluginAction
	@RunOnUiThread
	public void syncAction(Object _, StrawDrink drink) {
		drink.success();
	}

	@PluginAction
	public void actionThrows(Object _, StrawDrink drink) throws Exception {
		throw new Exception("abc");
	}
	
	@PluginAction
	private void actionWithWrongAccessModifier(Object _, StrawDrink drink) {
	}
}

@RunWith(RobolectricTestRunner.class)
public class StrawPluginActionTest {

	@Test
	public void testInvokeBackgroundAction() throws SecurityException, NoSuchMethodException {

		// create fixture plugin
		StrawPlugin plugin = new StrawPluginFixture();

		// get action method
		Method pluginAction = StrawPluginFixture.class.getMethod("action", Object.class, StrawDrink.class);

		StrawPluginAction action = new StrawPluginAction(plugin, pluginAction, "action", Object.class, true);

		// mock up argument object
		Object argumentObject = mock(Object.class);

		// mock up StrawDrink object
		StrawDrink drink = mock(StrawDrink.class);

		// invoke action
		action.invoke(argumentObject, drink);

		// verify plugin action invocation
		verify(drink, timeout(1000)).success();
	}


	@Test
	public void testInvokeSyncAction() throws SecurityException, NoSuchMethodException {

		// create fixture plugin
		StrawPlugin plugin = new StrawPluginFixture();

		// get action method
		Method pluginAction = StrawPluginFixture.class.getMethod("syncAction", Object.class, StrawDrink.class);

		StrawPluginAction action = new StrawPluginAction(plugin, pluginAction, "syncAction", Object.class, false);

		// mock up argument object
		Object argumentObject = mock(Object.class);

		// mock up StrawDrink object
		StrawDrink drink = mock(StrawDrink.class);

		// invoke action
		action.invoke(argumentObject, drink);

		// verify plugin action invocation
		verify(drink).success();
	}


	@Test
	public void testInvokeActionThrows() throws SecurityException, NoSuchMethodException {

		// create fixture plugin
		StrawPlugin plugin = new StrawPluginFixture();

		// get action method
		Method pluginAction = StrawPluginFixture.class.getMethod("actionThrows", Object.class, StrawDrink.class);

		// create action
		StrawPluginAction action = new StrawPluginAction(plugin, pluginAction, "actionThrows", Object.class, true);

		// mock up argument object
		Object argumentObject = mock(Object.class);

		// mock up StrawDrink object
		StrawDrink drink = mock(StrawDrink.class);
		when(drink.toString()).thenReturn("blah blah");

		// mock up printer
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		// invoke action
		action.invoke(argumentObject, drink);
		
		// verify
		verify(printer, timeout(1000).times(2)).println("Straw Framework Error: cannot invoke action method (invocation target exception): blah blah");
		verify(printer, timeout(1000)).println("java.lang.reflect.InvocationTargetException");
		verify(printer, timeout(1000)).println("java.lang.Exception: abc");

	}

	@Test
	public void testInvokeActionWithWrongAccessModifier() throws SecurityException, NoSuchMethodException {

		// create fixture plugin
		StrawPlugin plugin = new StrawPluginFixture();

		// get action method
		Method pluginAction = StrawPluginFixture.class.getDeclaredMethod("actionWithWrongAccessModifier", Object.class, StrawDrink.class);

		// create action
		StrawPluginAction action = new StrawPluginAction(plugin, pluginAction, "actionWithWrongAccessModifier", Object.class, true);

		// mock up argument object
		Object argumentObject = mock(Object.class);

		// mock up StrawDrink object
		StrawDrink drink = mock(StrawDrink.class);
		when(drink.toString()).thenReturn("blah blah");

		// mock up printer
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		// invoke action
		action.invoke(argumentObject, drink);
		
		// verify
		verify(printer, timeout(1000)).println("Straw Framework Error: cannot invoke action method (illegal access exception): blah blah");
		verify(printer, timeout(1000)).println("java.lang.IllegalAccessException: Class org.kt3k.straw.StrawPluginAction can not access a member of class org.kt3k.straw.StrawPluginFixture with modifiers \"private\"");

	}

}
