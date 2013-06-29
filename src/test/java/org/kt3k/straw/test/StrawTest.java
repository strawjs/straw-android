package org.kt3k.straw.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import org.kt3k.straw.*;

@RunWith(RobolectricTestRunner.class)
public class StrawTest {

    @Test
    public void testMe() {
        assertTrue(true);
    }

    @Test
    public void testHello() {
        assertEquals("hello", Straw.hello());
    }
}
