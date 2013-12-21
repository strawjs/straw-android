package org.kt3k.straw;

import org.junit.Test;
import static org.junit.Assert.*;

public class StrawEventTest {

	@Test
	public void testConstructor() {
		assertNotNull(new StrawEvent(""));
	}


	@Test
	public void testGetType() {
		StrawEvent event = new StrawEvent("typeName");

		assertEquals("typeName", event.getType());
	}


	@Test
	public void testToString() {
		StrawEvent event = new StrawEvent("typeName");

		assertEquals("StrawEvent (type=typeName)", event.toString());
	}

}
