package org.kt3k.straw;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.webkit.WebView;
import android.app.Activity;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawPluginTest {

    private final Activity activity = new Activity();
    private final WebView webView = new WebView(activity);
    private final StrawPluginManager manager = new StrawPluginManager(webView);

    private StrawPlugin dummyPlugin;

    @Before
    public void setUpDummyPlugin() throws Exception {
        this.manager.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");
        this.dummyPlugin = manager.getPluginByName("dummy");
    }

    @Test
    public void testPluginActionExecution() {

        assertEquals("{\"c\":\"foo\",\"d\":\"bar\"}", this.dummyPlugin.exec("dummyAction", "{\"a\":\"foo\",\"b\":\"bar\"}"));
        assertEquals("{\"c\":\"baz\",\"d\":\"baz\"}", this.dummyPlugin.exec("dummyAction", "{\"a\":\"baz\",\"b\":\"baz\"}"));

        assertEquals("null", this.dummyPlugin.exec("actionReturnsNull", null));
        assertEquals("{\"c\":null,\"d\":null}", this.dummyPlugin.exec("dummyAction3", null));
        assertEquals("{\"c\":\"ddd\",\"d\":null}", this.dummyPlugin.exec("dummyAction4", null));
    }

    @Test
    public void testActionWithoutAnnotation() {
        assertEquals(null, this.dummyPlugin.exec("actionWithoutAnnotation", null));
    }

}
