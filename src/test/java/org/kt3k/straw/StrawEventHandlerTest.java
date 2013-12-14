package org.kt3k.straw;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kt3k.straw.annotation.Background;
import org.kt3k.straw.annotation.EventHandler;
import org.kt3k.straw.annotation.RunOnUiThread;
import org.robolectric.RobolectricTestRunner;

import android.util.Printer;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class StrawEventHandlerTest {

	static class StrawPluginDummy extends StrawPlugin {

		@Override
		public String getName() {
			return "dummy";
		}

	}

	@Test
	public void testConstructor() throws SecurityException, NoSuchMethodException {
		assertNotNull(new StrawEventHandler(new StrawPluginDummy(), "event", this.getClass().getMethod("testConstructor", new Class[]{}), false));
	}

	@Test
	public void testGetters() throws SecurityException, NoSuchMethodException {
		StrawPlugin plugin = new StrawPluginDummy();
		Method method = this.getClass().getMethod("testGetters", new Class[]{});

		StrawEventHandler handler = new StrawEventHandler(plugin, "event", method, false);

		assertNotNull(handler);

		assertEquals(plugin, handler.getPlugin());
		assertEquals("event", handler.getEventName());
		assertEquals(method, handler.getMethod());
	}

	@Test
	public void testInvokeBackground() throws SecurityException, NoSuchMethodException {
		// mock up object
		final Straw mock = mock(Straw.class);

		// create fixture plugin
		StrawPlugin plugin = new StrawPlugin () {

			@Override
			public String getName() {
				return "name";
			}

			@Background
			@EventHandler("xyz_event")
			public void handler(StrawEvent e) {
				// dummy call of mocked method
				mock.postJsMessage("abc");
			}
		};

		Method method = plugin.getClass().getMethod("handler", new Class[]{StrawEvent.class});

		StrawEventHandler handler = new StrawEventHandler(plugin, "event", method, true);

		handler.invoke(mock(StrawEvent.class));

		verify(mock, timeout(1000)).postJsMessage("abc");
	}

	@Test
	public void testInvokeForeground() throws SecurityException, NoSuchMethodException {
		// mock up object
		final Straw mock = mock(Straw.class);

		// create fixture plugin
		StrawPlugin plugin = new StrawPlugin () {

			@Override
			public String getName() {
				return "name";
			}

			@RunOnUiThread
			@EventHandler("xyz_event")
			public void handler(StrawEvent e) {
				// dummy call of mocked method
				mock.postJsMessage("abc");
			}
		};

		// get method
		Method method = plugin.getClass().getMethod("handler", new Class[]{StrawEvent.class});

		// create handler object
		StrawEventHandler handler = new StrawEventHandler(plugin, "event", method, false);

		// invoke
		handler.invoke(mock(StrawEvent.class));

		verify(mock).postJsMessage("abc");
	}

	@Test
	public void testInvokeArgumentException() throws SecurityException, NoSuchMethodException {
		// mock up printer
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);
		StrawLog.setPrintStackTrace(false);

		// create fixture plugin
		StrawPlugin plugin = new StrawPlugin () {

			@Override
			public String getName() {
				return "name";
			}

			@RunOnUiThread
			@EventHandler("xyz_event")
			public void handler() {
			}
		};

		// get method
		Method method = plugin.getClass().getMethod("handler", new Class[]{});

		// create handler object
		StrawEventHandler handler = new StrawEventHandler(plugin, "event", method, false);

		// invoke
		handler.invoke(new StrawEvent("event"));

		verify(printer).println("Straw Framework Error: Cannot invoke event handler (illegal argument exception): StrawEvent (type=event)");
	}
}
