package org.kt3k.straw;

import android.webkit.WebView;

class MessageBackThread implements Runnable {

	WebView webView;
	String message;

	public MessageBackThread(WebView webView, String message) {
		this.webView = webView;
		this.message = message;
	}

	public void run() {
		this.webView.loadUrl(this.message);
	}

}