package org.kt3k.straw;

import android.util.Log;
import android.util.LogPrinter;
import android.util.Printer;

public class StrawLog {

	static final String MESSAGE_FRAMEWORK_ERROR = "Straw Framework Error: ";
	static final String MESSAGE_PLUGIN_ERROR = "Straw Plugin Error: ";

	static Printer output = new StrawErrorPrinter();

	static Boolean printStackTrace = true;

	private static void printError(String message, Exception e) {
		output.println(message);

		if (e != null) {
			output.println(e.toString());

			if (StrawLog.printStackTrace) {
				output.println(getStackTraceString(e));
			}
		}
	}

	public static void setPrinter(Printer printer) {
		StrawLog.output = printer;
	}

	public static Boolean getPrintStackTrace() {
		return StrawLog.printStackTrace;
	}

	public static void setPrintStackTrace(Boolean printStackTrace) {
		StrawLog.printStackTrace = printStackTrace;
	}

	private static String getStackTraceString(Exception e) {
		return StrawUtil.join(e.getStackTrace(), "\n");
	}

	public static void printFrameworkError(Exception e, String message) {
		printError(MESSAGE_FRAMEWORK_ERROR + message, e);
	}

	public static void printFrameworkError(String message) {
		printError(MESSAGE_FRAMEWORK_ERROR + message, null);
	}

	public static void printPluginError(Exception e, String message) {
		printError(MESSAGE_PLUGIN_ERROR + message, e);
	}

	public static void printPluginError(String message) {
		printError(MESSAGE_PLUGIN_ERROR + message, null);
	}
}

class StrawErrorPrinter extends LogPrinter {

	static String TAG = "Straw Framework";

	public StrawErrorPrinter() {
		super(Log.ERROR, TAG);
	}
}
