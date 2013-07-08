package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.webkit.WebView;
import android.app.Activity;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawPluginManagerTest {

    @Test
    public void testConstuctor() {
        Activity activity = new Activity();
        WebView webView = new WebView(activity);
        new StrawPluginManager(webView);
    }

    @Test
    public void testLoadPlugins() {
        Activity activity = new Activity();
        WebView webView = new WebView(activity);
        StrawPluginManager pm = new StrawPluginManager(webView);

        pm.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");

        StrawPlugin dummyPlugin = pm.getPluginByName("dummy");
        assertNotNull(dummyPlugin);
        assertEquals("org.kt3k.straw.DummyStrawPlugin", dummyPlugin.getClass().getCanonicalName());
    }

    @Test
    public void testPluginExecution() {
        Activity activity = new Activity();
        WebView webView = new WebView(activity);
        StrawPluginManager pm = new StrawPluginManager(webView);

        pm.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");

        StrawPlugin dummyPlugin = pm.getPluginByName("dummy");
        assertEquals("{\"c\":\"foo\",\"d\":\"bar\"}", dummyPlugin.exec("dummyAction", "{\"a\":\"foo\",\"b\":\"bar\"}"));
        assertEquals("{\"c\":\"baz\",\"d\":\"baz\"}", dummyPlugin.exec("dummyAction", "{\"a\":\"baz\",\"b\":\"baz\"}"));
    }

}
