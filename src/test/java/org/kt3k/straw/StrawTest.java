package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.webkit.WebView;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class StrawTest {

	@Test
	public void testConstuctor() {
		WebView webView = mock(WebView.class);
		Straw straw = new Straw(webView);

		assertNotNull(straw);

		verify(webView).addJavascriptInterface(isA(NativeToJsInterface.class), eq(Straw.JS_TO_NATIVE_INTERFACE_NAME));
	}

	@Test
	public void testInsertInto() {
		WebView webView = mock(WebView.class);
		Straw straw = Straw.insertInto(webView);

		assertNotNull(straw);

		verify(webView).addJavascriptInterface(isA(NativeToJsInterface.class), eq(Straw.JS_TO_NATIVE_INTERFACE_NAME));
	}

	@Test
	public void testGetRegistry() {
		WebView webView = mock(WebView.class);
		Straw straw = new Straw(webView);

		StrawPluginRegistry reg = straw.getRegistry();

		assertNotNull(reg);
	}

}
