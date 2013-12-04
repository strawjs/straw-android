package org.kt3k.straw;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

class StrawPluginFixture2 extends StrawPlugin {

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

	@Background
	@PluginAction
	public void backgroundAction(Object _, StrawDrink drink) {
		drink.success();
	}
	
	
	@PluginAction
	public void invalidParamAction(Object _, StrawDrink drink, Object __) {
	}
	

	@PluginAction
	public void invalidParamAction2(Object _, Object __) {
	}
	
}

@RunWith(RobolectricTestRunner.class)
public class StrawPluginActionFactoryTest {
	
	@Test
	public void testConstructor() {
		assertNotNull(new StrawPluginActionFactory());
	}
	
	@Test
	public void testCreatePluginAction() throws SecurityException, NoSuchMethodException {
		
		// create plugin
		StrawPlugin plugin = new StrawPluginFixture2();
		
		Method method = StrawPluginFixture2.class.getMethod("action", Object.class, StrawDrink.class);
		
		StrawPluginAction action = StrawPluginActionFactory.createPluginAction(method, plugin);
		
		assertEquals("action", action.getName());
		assertEquals(Object.class, action.getArgumentType());
		assertEquals(plugin, action.getPlugin());
		
	}
	
	
	@Test
	public void testCreatePluginActionWithBackgroundAnnotation() throws SecurityException, NoSuchMethodException {
		// create plugin
		StrawPlugin plugin = new StrawPluginFixture2();
		
		Method method = StrawPluginFixture2.class.getMethod("backgroundAction", Object.class, StrawDrink.class);
		
		StrawPluginAction action = StrawPluginActionFactory.createPluginAction(method, plugin);
		
		assertEquals("backgroundAction", action.getName());
		assertEquals(Object.class, action.getArgumentType());
		assertEquals(plugin, action.getPlugin());
		
	}
	
	
	@Test
	public void testCreatePluginActionWithRunOnUiThreadAnnotation() throws SecurityException, NoSuchMethodException {
		// create plugin
		StrawPlugin plugin = new StrawPluginFixture2();
		
		Method method = StrawPluginFixture2.class.getMethod("syncAction", Object.class, StrawDrink.class);
		
		StrawPluginAction action = StrawPluginActionFactory.createPluginAction(method, plugin);
		
		assertEquals("syncAction", action.getName());
		assertEquals(Object.class, action.getArgumentType());
		assertEquals(plugin, action.getPlugin());
		
	}

	
	@Test
	public void testCreatePluginActionWithInvalidParam() throws SecurityException, NoSuchMethodException {
		// create plugin
		StrawPlugin plugin = new StrawPluginFixture2();
		
		Method method = StrawPluginFixture2.class.getMethod("invalidParamAction", Object.class, StrawDrink.class, Object.class);
		
		StrawPluginAction action = StrawPluginActionFactory.createPluginAction(method, plugin);
		
		assertNull(action);
	}

	
	@Test
	public void testCreatePluginActionWithInvalidParam2() throws SecurityException, NoSuchMethodException {
		// create plugin
		StrawPlugin plugin = new StrawPluginFixture2();
		
		Method method = StrawPluginFixture2.class.getMethod("invalidParamAction2", Object.class, Object.class);
		
		StrawPluginAction action = StrawPluginActionFactory.createPluginAction(method, plugin);
		
		assertNull(action);
	}

}
