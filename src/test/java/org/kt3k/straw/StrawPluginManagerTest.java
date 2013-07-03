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

        List<String> list = new ArrayList<String>();
        list.add("org.kt3k.straw.DummyStrawPlugin");

        pm.loadPlugins(list);

        assertNotNull(pm.getPluginByName("dummy"));
    }

}
