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

	@Test
	public void testCostructor() {
		new StrawLog();
	}

	@Test
	public void testStackTracePrinting() {

		// mock up printer
		Printer printer = mock(Printer.class);
		StrawLog.setPrinter(printer);

		// set up dummy stack trace element
		// (I wanted to stub this, but final classes cannot be stubbed...)
		StackTraceElement elm = new StackTraceElement("a", "b", "c", 1);

		// mock up exception
		Exception e = mock(Exception.class);
		when(e.toString()).thenReturn("exception message");
		when(e.getStackTrace()).thenReturn(new StackTraceElement[]{elm, elm, elm});

		// printing stack trace flag true
		StrawLog.setPrintStackTrace(true);

		// test
		StrawLog.printFrameworkError(e, "abc");

		// verify
		verify(printer).println("Straw Framework Error: abc");
		verify(printer).println("exception message");
		verify(printer, times(3)).println("a.b(c:1)");

		// restore false state
		StrawLog.setPrintStackTrace(false);

	}

}
