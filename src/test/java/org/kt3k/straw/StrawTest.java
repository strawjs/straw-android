package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.webkit.WebView;
import android.app.Activity;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawTest {

	@Test
	public void testConstuctor() {
		Activity activity = new Activity();
		WebView webView = new WebView(activity);
		Straw straw = new Straw(webView);

		assertNotNull(straw);
	}

	@Test
	public void testGetRegistry() {
		Activity activity = new Activity();
		WebView webView = new WebView(activity);
		Straw straw = new Straw(webView);

		StrawPluginRegistry reg = straw.getRegistry();

		assertNotNull(reg);
	}

}
