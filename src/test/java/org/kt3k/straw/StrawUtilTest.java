package org.kt3k.straw;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawUtilTest {

	static class CustomClass {

		@Override
		public String toString() {
			return "CustomClass";
		}
	}

	@Test
	public void testJoin() {
		assertEquals("123:456", StrawUtil.join(new Integer[]{123, 456}, ":"));
		assertEquals("123:456:789", StrawUtil.join(new Integer[]{123, 456, 789}, ":"));
		assertEquals("abc, def", StrawUtil.join(new String[]{"abc", "def"}, ", "));
		assertEquals("abc, def, ghi", StrawUtil.join(new String[]{"abc", "def", "ghi"}, ", "));
		assertEquals("CustomClass CustomClass", StrawUtil.join(new CustomClass[]{new CustomClass(), new CustomClass()}, " "));
		assertEquals("CustomClass CustomClass CustomClass", StrawUtil.join(new CustomClass[]{new CustomClass(), new CustomClass(), new CustomClass()}, " "));
	}

	@Test
	public void testEmptyJoin() {
		assertEquals("", StrawUtil.join(new Integer[]{}, ", "));
	}

	static class DummyClass {
		public String foo;
		public Integer bar;
	}

	@Test
	public void testObjToJson() {
		DummyClass a = new DummyClass();
		a.foo = "abc";
		a.bar = 333;

		assertEquals("{\"foo\":\"abc\",\"bar\":333}", StrawUtil.objToJson(a));
	}

	@Test
	public void testJsonToObj() {
		DummyClass a = StrawUtil.jsonToObj("{\"foo\":\"def\",\"bar\":444}", DummyClass.class);
		assertEquals("def", a.foo);
		assertEquals((Integer)444, a.bar);
	}

	@Test
	public void testConstructor() {
		new StrawUtil();
	}
	
	public void testHasAnnotation() throws SecurityException, NoSuchMethodException {
		Method method = this.getClass().getMethod("testHasAnnotation", new Class[]{});
		
		assertTrue(StrawUtil.hasAnnotation(method, Test.class));
		
		assertFalse(StrawUtil.hasAnnotation(method, Before.class));
	}

}
