package org.kt3k.straw;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.webkit.WebView;
import android.app.Activity;
import android.os.Handler;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StrawTest {

    @Test
    public void testMe() {
        assertTrue(true);
    }

    @Test
    public void testConstuctor() {
        Activity activity = new Activity();
        WebView webView = new WebView(activity);
        Handler handler = new Handler();
        Straw straw = new Straw(webView, activity, handler);
    }

}
