package org.kt3k.straw;

import java.lang.reflect.Method;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class StrawEventHandlerTest {

	@Test
	public void testConstructor() throws SecurityException, NoSuchMethodException {
		assertNotNull(new StrawEventHandler(mock(StrawPlugin.class), "event", this.getClass().getMethod("testConstructor", new Class[]{}), false));
	}

	@Test
	public void testGetters() throws SecurityException, NoSuchMethodException {
		StrawPlugin plugin = mock(StrawPlugin.class);
		Method method = this.getClass().getMethod("testGetters", new Class[]{});

		StrawEventHandler handler = new StrawEventHandler(plugin, "event", method, false);

		assertNotNull(handler);

		assertEquals(plugin, handler.getPlugin());
		assertEquals("event", handler.getEventName());
		assertEquals(method, handler.getMethod());
	}
}
