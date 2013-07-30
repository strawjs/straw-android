package org.kt3k.straw;

import android.util.Log;
import android.util.LogPrinter;

class StrawLogErrorPrinter extends LogPrinter {

	static String TAG = "Straw Framework";

	public StrawLogErrorPrinter() {
		super(Log.ERROR, TAG);
	}
}