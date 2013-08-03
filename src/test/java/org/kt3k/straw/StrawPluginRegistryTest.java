package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import android.webkit.WebView;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawPluginRegistryTest {

    @Test
    public void testConstuctor() {
        new StrawPluginRegistry(mock(WebView.class));
    }

    @Test
    public void testLoadPluginsByName() {
        StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

        registry.loadPlugins(new String[]{"org.kt3k.straw.DummyStrawPlugin"});

        StrawPlugin dummyPlugin = registry.getPluginByName("dummy");
        assertNotNull(dummyPlugin);
        assertEquals("org.kt3k.straw.DummyStrawPlugin", dummyPlugin.getClass().getCanonicalName());
    }

    @Test
    public void testLoadPluginByName() {
        StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

        registry.loadPluginByName("org.kt3k.straw.DummyStrawPlugin");

        StrawPlugin dummyPlugin = registry.getPluginByName("dummy");
        assertNotNull(dummyPlugin);
        assertEquals("org.kt3k.straw.DummyStrawPlugin", dummyPlugin.getClass().getCanonicalName());
    }

    @Test
    public void testLoadPluginByClass() {
        StrawPluginRegistry registry = new StrawPluginRegistry(mock(WebView.class));

        registry.loadPluginByClass(org.kt3k.straw.DummyStrawPlugin.class);

        StrawPlugin dummyPlugin = registry.getPluginByName("dummy");
        assertNotNull(dummyPlugin);
        assertEquals("org.kt3k.straw.DummyStrawPlugin", dummyPlugin.getClass().getCanonicalName());
    }

}
