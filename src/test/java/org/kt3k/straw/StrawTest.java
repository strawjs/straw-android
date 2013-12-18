package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kt3k.straw.annotation.EventHandler;

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

		verify(webView).addJavascriptInterface(isA(JsToNativeInterface.class), eq(Straw.JS_TO_NATIVE_INTERFACE_NAME));
	}

	@Test
	public void testInsertInto() {
		WebView webView = mock(WebView.class);
		Straw straw = Straw.insertInto(webView);

		assertNotNull(straw);

		verify(webView).addJavascriptInterface(isA(JsToNativeInterface.class), eq(Straw.JS_TO_NATIVE_INTERFACE_NAME));
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

		JsToNativeInterface js2n = new JsToNativeInterfaceImpl(spy);

		js2n.exec("dummy", "dummyAction", "{\"a\": \"1\", \"b\": \"3\"}", "abc");

		verify(spy, timeout(1000)).postJsMessage("javascript:" + Straw.NATIVE_TO_JS_INTERFACE_NAME + ".exec(\"abc\",true,{\"c\":\"1\",\"d\":\"3\"},false);");
	}

	@Test
	public void testAddPlugins() {
		Straw straw = Straw.insertInto(mock(WebView.class));

		straw.addPlugins(new String[]{"org.kt3k.straw.DummyStrawPlugin"});

		assertNotNull(straw.getRegistry().getActionRepositoryForPluginName("dummy"));

		straw.removePlugin("dummy");

		assertNull(straw.getRegistry().getActionRepositoryForPluginName("dummy"));

		straw.addPlugins(new String[]{"org.kt3k.straw.DummyStrawPlugin"});

		assertNotNull(straw.getRegistry().getActionRepositoryForPluginName("dummy"));

		straw.clearPlugins();

		assertNull(straw.getRegistry().getActionRepositoryForPluginName("dummy"));
	}


	@Test
	public void testRootHandler() {
		Straw straw = Straw.insertInto(mock(WebView.class));

		final Straw mock = mock(Straw.class);

		StrawPlugin plugin = new StrawPlugin () {

			@Override
			public String getName() {
				return "dummy";
			}

			@EventHandler("my sister arrested")
			public void handler(StrawEvent e) {
				// dummy call for verification
				mock.getRegistry();
			}

		};

		straw.getRegistry().loadPlugin(plugin);

		straw.rootHandler(new StrawEvent("my sister arrested"));

		// verify dummy call inside handler
		verify(mock).getRegistry();
	}


	@Test
	public void testOnBackPressed() {
		Straw straw = Straw.insertInto(mock(WebView.class));

		final Straw mock = mock(Straw.class);

		StrawPlugin plugin = new StrawPlugin () {

			@Override
			public String getName() {
				return "dummy";
			}

			@EventHandler(StrawEvent.Type.BACK_PRESSED)
			public void handler(StrawEvent e) {
				// dummy call for verification
				mock.getRegistry();
			}

		};

		straw.getRegistry().loadPlugin(plugin);

		straw.onBackPressed();

		// verify dummy call inside handler
		verify(mock).getRegistry();
	}

}
