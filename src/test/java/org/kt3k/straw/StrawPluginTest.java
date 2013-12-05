package org.kt3k.straw;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import android.webkit.WebView;
import android.app.Activity;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawPluginTest {

	private StrawPluginRegistry registry;

	private StrawPlugin dummyPlugin;
	@Before
	public void setUpDummyPlugin() throws Exception {
		WebView webView = mock(WebView.class);
		when(webView.getContext()).thenReturn(mock(Activity.class));

		this.registry = new StrawPluginRegistry(webView);
		this.registry.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");

		this.dummyPlugin = registry.getActionRepositoryForPluginName("dummy").get("dummyAction").getPlugin();

		mock(StrawDrink.class);
	}

	@Test
	public void testWebViewIsNotNull() {
		assertNotNull(this.dummyPlugin.webView);
	}

	@Test
	public void testContextIsNotNull() {
		assertNotNull(this.dummyPlugin.context);
	}

	@Test
	public void testActivityIsNotNull() {
		assertNotNull(this.dummyPlugin.activity);
	}

	@Test
	public void testSingleStringParam() {
		assertNotNull(new StrawPlugin.SingleStringParam());
	}


	@Test
	public void testSingleIntegerParam() {
		assertNotNull(new StrawPlugin.SingleIntegerParam());
	}
}
