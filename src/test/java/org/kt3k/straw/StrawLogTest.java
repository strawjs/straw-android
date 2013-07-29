package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.robolectric.RobolectricTestRunner;

import android.util.Printer;

@RunWith(RobolectricTestRunner.class)
public class StrawLogTest {

	@Test
	public void testPrintFrameworkError() {
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);

		StrawLog.printFrameworkError("abc");
		verify(printer).println("Straw Framework Error: abc");

		StrawLog.printFrameworkError(new Exception("abc"), "def");
		verify(printer).println("Straw Framework Error: def");
		verify(printer).println("java.lang.Exception: abc");
	}

	@Test
	public void testPrintPluginError() {
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);

		StrawLog.printPluginError("abc");
		verify(printer).println("Straw Plugin Error: abc");

		StrawLog.printPluginError(new Exception("abc"), "def");
		verify(printer).println("Straw Plugin Error: def");
		verify(printer).println("java.lang.Exception: abc");
	}

	@Test
	public void testSetPrintStackTrace() {
		StrawLog.setPrintStackTrace(true);

		assertTrue(StrawLog.getPrintStackTrace());

		StrawLog.setPrintStackTrace(false);

		assertFalse(StrawLog.getPrintStackTrace());
	}

	@Test public void testCostructor() {
		new StrawLog();
	}

}
