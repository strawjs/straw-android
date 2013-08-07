package org.kt3k.straw.plugin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.kt3k.straw.StrawDrink;
import org.kt3k.straw.StrawPlugin;
import org.kt3k.straw.PluginAction;

public class HttpPlugin extends StrawPlugin {

	static final String URL_MALFORMED_ERROR = "0";
	static final String CANNOT_CONNECT_ERROR = "1";
	static final String CANNOT_READ_ERROR = "2";

	@Override
	public String getName() {
		return "http";
	}

	public static class HttpParam {
		public String url;
		public String data;
	}

	public static class HttpResult {
		public String content;

		public HttpResult(String content) {
			this.content = content;
		}
	}

	@PluginAction
	public void get(HttpParam param, StrawDrink drink) {
		URL url = null;
		HttpURLConnection conn = null;
		String content = null;

		try {
			url = new URL(param.url);
		} catch (MalformedURLException e) {
			drink.fail(URL_MALFORMED_ERROR, "URL format is wrong: " + param.url);

			return;
		}

		try {
			conn = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			drink.fail(CANNOT_CONNECT_ERROR, "cannot connect to url: " + param.url);

			return;
		}

		try {
			content = inputStreamToString(conn.getInputStream());
		} catch (IOException e) {
			drink.fail(CANNOT_READ_ERROR, "input stream cannot open: " + param.url);

			return;
		}

		drink.success(new HttpResult(content));
	}

	@PluginAction
	public void post(HttpParam param, StrawDrink drink) {
	}

	private static String inputStreamToString(java.io.InputStream stream) {
		return new java.util.Scanner(stream).useDelimiter("\\A").next();
	}
}
