package org.kt3k.straw.plugin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.kt3k.straw.StrawDrink;
import org.kt3k.straw.StrawPlugin;
import org.kt3k.straw.PluginAction;

public class HttpPlugin extends StrawPlugin {

	static final String URL_MALFORMED_ERROR = "0";
	static final String CANNOT_CONNECT_ERROR = "1";
	static final String CANNOT_READ_ERROR = "2";
	static final String SSL_UNAVAILABLE = "3";

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
		HttpURLConnection conn;

		try {
			conn = createConnection(param.url);
		} catch (MalformedURLException e) {
			drink.fail(URL_MALFORMED_ERROR, "URL format is wrong: " + param.url);

			return;
		} catch (IOException e) {
			drink.fail(CANNOT_CONNECT_ERROR, "cannot connect to url: " + param.url);

			return;
		}

		if (param.url.startsWith("https")) {
			try {
				setSSLContext(conn);

			} catch (NoSuchAlgorithmException e) {
				drink.fail(SSL_UNAVAILABLE, "SSL connection is unavailable: " + param.url);

				return;
			} catch (KeyManagementException e) {
				drink.fail(SSL_UNAVAILABLE, "SSL connection is unavailable: " + param.url);

				return;
			}
		}

		String content = null;

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
		Scanner scanner = new Scanner(stream).useDelimiter("\\A");

		return scanner.hasNext() ? scanner.next() : "";
	}

	private static HttpURLConnection createConnection(String spec) throws MalformedURLException, IOException {
		URL url = new URL(spec);

		HttpURLConnection conn = (HttpURLConnection)url.openConnection();

		return conn;
	}

	private static void setSSLContext(HttpURLConnection conn) throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext ctx = SSLContext.getInstance("TLS");

		ctx.init(null, new X509TrustManager[]{new X509TrustManager(){
			public void checkClientTrusted(X509Certificate[] chain,String authType) {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) {}
			public X509Certificate[] getAcceptedIssuers() { return null; }
		}}, null);

		((HttpsURLConnection)conn).setSSLSocketFactory(ctx.getSocketFactory());
	}
}
