package org.kt3k.straw;

import java.util.Comparator;
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
		
		// create handlerArray to sort its contents
		StrawEventHandler[] handlerArray = handlers.toArray(new StrawEventHandler[handlers.size()]);
		java.util.Arrays.sort(handlerArray, new Comparator<StrawEventHandler>() {

			@Override
			public int compare(StrawEventHandler arg0, StrawEventHandler arg1) {
				return arg0.getEventName().compareTo(arg1.getEventName());
			}
			
		});
		
		assertEquals(4, handlerArray.length);
		assertEquals("backPressed", handlerArray[0].getEventName());
		assertEquals("event1", handlerArray[1].getEventName());
		assertEquals("event2", handlerArray[2].getEventName());
		assertEquals("event3", handlerArray[3].getEventName());
	}

}
