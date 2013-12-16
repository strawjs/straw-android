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
		
		assertEquals(3, handlers.size());
	}

}
