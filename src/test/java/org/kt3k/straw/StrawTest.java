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

	@Test
	public void testPluginActionInvocation() throws InterruptedException {
		Straw straw = Straw.insertInto(mock(WebView.class));
		Straw spy = spy(straw);

		spy.addPlugin("org.kt3k.straw.DummyStrawPlugin");

		NativeToJsInterface n2js = new NativeToJsInterfaceImpl(spy);

		n2js.exec("dummy", "dummyAction", "{\"a\": \"1\", \"b\": \"3\"}", "abc");

		verify(spy, timeout(1000)).postJsMessage("javascript:" + Straw.NATIVE_TO_JS_INTERFACE_NAME + ".exec(\"abc\",true,{\"c\":\"1\",\"d\":\"3\"},false);");

	}

}
