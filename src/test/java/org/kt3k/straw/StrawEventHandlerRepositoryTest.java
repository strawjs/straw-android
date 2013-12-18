package org.kt3k.straw;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class StrawEventHandlerRepositoryTest {

	@Test
	public void testStore() {
		StrawEventHandlerRepository repository = new StrawEventHandlerRepository();

		StrawEventHandler handler0 = new StrawEventHandler(mock(StrawPlugin.class), "event0", null, false);
		StrawEventHandler handler1 = new StrawEventHandler(mock(StrawPlugin.class), "event1", null, false);
		StrawEventHandler handler2 = new StrawEventHandler(mock(StrawPlugin.class), "event1", null, false);

		repository.store(handler0);
		repository.store(handler1);
		repository.store(handler2);

		assertEquals(handler0, repository.get("event0").get(0));
		assertEquals(handler1, repository.get("event1").get(0));
		assertEquals(handler2, repository.get("event1").get(1));
	}


	@Test
	public void testStoreMultiple() {
		StrawEventHandlerRepository repository = new StrawEventHandlerRepository();

		StrawEventHandler handler0 = new StrawEventHandler(mock(StrawPlugin.class), "event0", null, false);
		StrawEventHandler handler1 = new StrawEventHandler(mock(StrawPlugin.class), "event1", null, false);
		StrawEventHandler handler2 = new StrawEventHandler(mock(StrawPlugin.class), "event1", null, false);

		List<StrawEventHandler> list = new ArrayList<StrawEventHandler>();

		list.add(handler0);
		list.add(handler1);
		list.add(handler2);

		repository.store(list);

		assertEquals(handler0, repository.get("event0").get(0));
		assertEquals(handler1, repository.get("event1").get(0));
		assertEquals(handler2, repository.get("event1").get(1));
	}


	@Test
	public void testStoreNull() {
		StrawEventHandlerRepository repository = new StrawEventHandlerRepository();

		// raise no error
		repository.store((StrawEventHandler)null);
		repository.store((List<StrawEventHandler>)null);
	}


}
