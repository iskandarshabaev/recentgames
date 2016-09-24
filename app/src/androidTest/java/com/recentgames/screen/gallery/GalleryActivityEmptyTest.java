package com.recentgames.screen.gallery;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.recentgames.screen.details.GameDetailsActivity;
import com.recentgames.screen.reviews.ReviewActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GalleryActivityEmptyTest {

    @Rule
    public ActivityTestRule<GalleryActivity> mActivityRule =
            new ActivityTestRule<>(GalleryActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testContentLoadedVisible(){
        launchActivity();
    }

    private void launchActivity() {
        Intent intent = new Intent(InstrumentationRegistry.getContext(), GalleryActivity.class);
        mActivityRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
