package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

import android.webkit.WebView;

@RunWith(RobolectricTestRunner.class)
public class MessageBackThreadTest {
	
	@Test
	public void testRun() {
		WebView webView = mock(WebView.class);
		String message = "abc";
		
		new MessageBackThread(webView, message).run();
		
		verify(webView).loadUrl(message);
	}

}
