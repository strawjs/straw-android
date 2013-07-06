package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.webkit.WebView;
import android.app.Activity;

import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class StrawPluginManagerTest {

    @Test
    public void testConstuctor() {
        Activity activity = new Activity();
        WebView webView = new WebView(activity);
        StrawPluginManager pm = new StrawPluginManager(webView, activity);
    }

    @Test
    public void testLoadPlugins() {
        Activity activity = new Activity();
        WebView webView = new WebView(activity);
        StrawPluginManager pm = new StrawPluginManager(webView, activity);

        pm.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");

        StrawPlugin dummyPlugin = pm.getPluginByName("dummy");
        assertNotNull(dummyPlugin);
        assertEquals("org.kt3k.straw.DummyStrawPlugin", dummyPlugin.getClass().getCanonicalName());
    }

    @Test
    public void testPluginExecution() {
        Activity activity = new Activity();
        WebView webView = new WebView(activity);
        StrawPluginManager pm = new StrawPluginManager(webView, activity);

        pm.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");

        StrawPlugin dummyPlugin = pm.getPluginByName("dummy");
        assertEquals("{\"c\":\"foo\",\"d\":\"bar\"}", dummyPlugin.exec("dummyAction", "{\"a\":\"foo\",\"b\":\"bar\"}"));
        assertEquals("{\"c\":\"baz\",\"d\":\"baz\"}", dummyPlugin.exec("dummyAction", "{\"a\":\"baz\",\"b\":\"baz\"}"));

        assertEquals("null", dummyPlugin.exec("dummyAction2", null));
        assertEquals("{\"c\":null,\"d\":null}", dummyPlugin.exec("dummyAction3", null));
        assertEquals("{\"c\":\"ddd\",\"d\":null}", dummyPlugin.exec("dummyAction4", null));
    }

}
