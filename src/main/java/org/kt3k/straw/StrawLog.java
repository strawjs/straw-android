package org.kt3k.straw;

public class StrawLog {
	
	static final String MESSAGE_FRAMEWORK_ERROR = "Straw Framework Error: ";
	static final String MESSAGE_PLUGIN_ERROR = "Straw Plugin Error: ";

	private static void printError(String message, Exception e) {
		System.out.println(message);

		if (e != null) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public static void printFrameworkError(String message, Exception e) {
		printError(MESSAGE_FRAMEWORK_ERROR + message, e);
	}
	
	public static void printPluginError(String message, Exception e) {
		printError(MESSAGE_PLUGIN_ERROR + message, e);
	}
}
