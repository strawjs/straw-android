package org.kt3k.straw.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.webkit.WebView;
import android.os.Handler;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import org.kt3k.straw.Straw;

@RunWith(RobolectricTestRunner.class)
public class StrawTest {

    @Test
    public void testMe() {
        assertTrue(true);
    }

    @Test
    public void testConstuctor() {
        WebView webView = new android.webkit.WebView(new android.app.Activity());
        Handler handler = new android.os.Handler();
        Straw straw = new Straw(webView, handler);
    }

}
