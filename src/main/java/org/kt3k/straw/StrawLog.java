package org.kt3k.straw;

import android.util.Log;
import android.util.LogPrinter;
import android.util.Printer;

public class StrawLog {

	static final String MESSAGE_FRAMEWORK_ERROR = "Straw Framework Error: ";
	static final String MESSAGE_PLUGIN_ERROR = "Straw Plugin Error: ";

	static Printer output = new StrawErrorPrinter();

	private static void printError(String message, Exception e) {
		output.println(message);

		if (e != null) {
			output.println(e.toString());
			output.println(StrawUtil.join(e.getStackTrace(), "\n"));
		}
	}

	public static void setPrinter(Printer printer) {
		output = printer;
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
