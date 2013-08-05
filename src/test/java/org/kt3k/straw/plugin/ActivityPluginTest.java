package org.kt3k.straw.plugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kt3k.straw.StrawDrink;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import android.app.Activity;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ActivityPluginTest {

	@Test
	public void testGetName() {
		assertEquals("activity", new ActivityPlugin().getName());
	}

	@Test
	public void testFinish() {
		ActivityPlugin plugin = new ActivityPlugin();
		Activity activity = mock(Activity.class);
		plugin.setContext(activity);

		plugin.finish(new Object(), mock(StrawDrink.class));

		verify(activity).finish();
	}

}
