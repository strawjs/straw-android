package org.kt3k.straw;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class StrawEventHandlerFactoryTest {

	@Test
	public void testConstructor() {
		assertNotNull(new StrawEventHandlerFactory());
	}
	
	
	@Test
	public void testCreateWithPlugins() {
		List<StrawEventHandler> handlers = StrawEventHandlerFactory.create(new StrawPlugin[]{new DummyStrawPlugin()});
		
		assertEquals(4, handlers.size());
		assertEquals("backPressed", handlers.get(0).getEventName());
		assertEquals("event1", handlers.get(1).getEventName());
		assertEquals("event2", handlers.get(2).getEventName());
		assertEquals("event3", handlers.get(3).getEventName());
	}

}
