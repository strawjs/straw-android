package org.kt3k.straw;

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

}
