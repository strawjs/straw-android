package org.kt3k.straw.plugin;

import org.kt3k.straw.PluginAction;
import org.kt3k.straw.StrawDrink;

import android.widget.Toast;

public class UIPlugin extends org.kt3k.straw.StrawPlugin {

	@Override
	public String getName() {
		return "UI";
	}

	static class ToastParam {
		public String text;
	}

	@PluginAction
	public void toast(ToastParam param, StrawDrink drink) {
		Toast.makeText(activity, param.text, Toast.LENGTH_SHORT).show();
	}

	@PluginAction
	public void toastLong(ToastParam param, StrawDrink drink) {
		Toast.makeText(activity, param.text, Toast.LENGTH_LONG).show();
	}



}
