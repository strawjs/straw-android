package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import android.app.Activity;
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

		StrawPluginActionRepository repo = registry.getActionRepositoryForPluginName("dummy");
		assertNotNull(repo);

		StrawPluginAction action = repo.get("dummyAction");
		assertNotNull(action);
	}


	@Test
	public void testLoadPluginByName() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		registry.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");

		StrawPluginActionRepository repo = registry.getActionRepositoryForPluginName("dummy");
		assertNotNull(repo);

		StrawPluginAction action = repo.get("dummyAction");
		assertNotNull(action);
	}

	@Test
	public void testLoadPluginByClass() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		registry.loadPluginByClass(org.kt3k.straw.DummyStrawPlugin.class);

		StrawPluginActionRepository repo = registry.getActionRepositoryForPluginName("dummy");
		assertNotNull(repo);

		StrawPluginAction action = repo.get("dummyAction");
		assertNotNull(action);
	}

	@Test
	public void testClassNotFoundException() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		registry.loadPluginByName("org.nonexistent.NonExistent");

		verify(printer).println("Straw Framework Error: Class not found: class=org.nonexistent.NonExistent");
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

		verify(printer).println("Straw Framework Error: Cannot instantiate plugin class: class=org.kt3k.straw.StrawPlugin");
		verify(printer).println("java.lang.InstantiationException");
	}

	@Test
	public void testLoadPluginWithoutCorrectConstructor() {
		StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		registry.loadPluginByName("org.kt3k.straw.PluginWithoutCorrectConstructor");

		verify(printer).println("Straw Framework Error: Illegal access: class=org.kt3k.straw.PluginWithoutCorrectConstructor");
		verify(printer).println("java.lang.IllegalAccessException: Class org.kt3k.straw.StrawPluginRegistry can not access a member of class org.kt3k.straw.PluginWithoutCorrectConstructor with modifiers \"private\"");
	}


	@Test
	public void testLoadPluginWithoutError() {
		WebView webView = mock(WebView.class);
		when(webView.getContext()).thenReturn(mock(Activity.class));
		StrawPluginRegistry registry = new StrawPluginRegistry(webView);

		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		registry.loadPluginByName("org.kt3k.straw.PluginWithoutError");
	}

}
