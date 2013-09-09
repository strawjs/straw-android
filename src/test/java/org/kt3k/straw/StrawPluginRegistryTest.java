package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import android.util.Printer;
import android.webkit.WebView;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawPluginRegistryTest {

	@Test
	public void testConstuctor() {
		new StrawPluginRegistry(mock(WebView.class));
	}

	@Test
	public void testLoadPluginsByName() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		registry.loadPlugins(new String[]{"org.kt3k.straw.DummyStrawPlugin"});

		StrawPlugin dummyPlugin = registry.getPluginByName("dummy");
		assertNotNull(dummyPlugin);
		assertEquals("org.kt3k.straw.DummyStrawPlugin", dummyPlugin.getClass().getCanonicalName());
	}


	@Test
	public void testLoadPluginByName() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		registry.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");

		StrawPlugin dummyPlugin = registry.getPluginByName("dummy");
		assertNotNull(dummyPlugin);
		assertEquals("org.kt3k.straw.DummyStrawPlugin", dummyPlugin.getClass().getCanonicalName());
	}

	@Test
	public void testLoadPluginByClass() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		registry.loadPluginByClass(org.kt3k.straw.DummyStrawPlugin.class);

		StrawPlugin dummyPlugin = registry.getPluginByName("dummy");
		assertNotNull(dummyPlugin);
		assertEquals("org.kt3k.straw.DummyStrawPlugin", dummyPlugin.getClass().getCanonicalName());
	}

	@Test
	public void testClassNotFoundException() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		registry.loadPluginByName("org.nonexistent.NonExistent");

		verify(printer).println("Straw Framework Error: class not found: class=org.nonexistent.NonExistent");
		verify(printer).println("java.lang.ClassNotFoundException: org.nonexistent.NonExistent");
	}

	@Test
	public void testLoadPluginByNull() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		registry.loadPlugin(null);
	}

	@Test
	public void testLoadPluginWithoutName() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		registry.loadPluginByName("org.kt3k.straw.PluginWithoutName");

		verify(printer).println("Straw Framework Error: Plugin name is null");
	}

	@Test
	public void testLoadAbstractPlugin() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		registry.loadPluginByName("org.kt3k.straw.StrawPlugin");

		verify(printer).println("Straw Framework Error: cannot instantiate plugin class: class=org.kt3k.straw.StrawPlugin");
		verify(printer).println("java.lang.InstantiationException");
	}

	@Test
	public void testLoadPluginWithoutCorrectConstructor() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		registry.loadPluginByName("org.kt3k.straw.PluginWithoutCorrectConstructor");

		verify(printer).println("Straw Framework Error: illegal access: class=org.kt3k.straw.PluginWithoutCorrectConstructor");
		verify(printer).println("java.lang.IllegalAccessException: Class org.kt3k.straw.StrawPluginRegistry can not access a member of class org.kt3k.straw.PluginWithoutCorrectConstructor with modifiers \"private\"");
	}


	@Test
	public void testLoadPluginWithoutError() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		registry.loadPluginByName("org.kt3k.straw.PluginWithoutError");

		verify(printer).println("abc");
		verify(printer).println("def");
	}

}
