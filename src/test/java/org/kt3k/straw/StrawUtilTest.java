package org.kt3k.straw;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawUtilTest {

	@Test
	public void testJoin() {
		assertEquals("123:456", StrawUtil.join(new Integer[]{123, 456}, ":"));
		assertEquals("abc, def", StrawUtil.join(new String[]{"abc", "def"}, ", "));
	}

	static class DummyClass {
		public String foo;
		public Integer bar;
	}

	@Test
	public void testObjToJson() throws JsonGenerationException, JsonMappingException, IOException {
		DummyClass a = new DummyClass();
		a.foo = "abc";
		a.bar = 333;

		assertEquals("{\"foo\":\"abc\",\"bar\":333}", StrawUtil.objToJson(a));
	}

	@Test
	public void testJsonToObj() throws JsonParseException, JsonMappingException, IOException {
		DummyClass a = StrawUtil.jsonToObj("{\"foo\":\"def\",\"bar\":444}", DummyClass.class);
		assertEquals("def", a.foo);
		assertEquals((Integer)444, a.bar);
	}

}
