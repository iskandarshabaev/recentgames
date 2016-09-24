package com.recentgames.screen.reviews;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.recentgames.R;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.screen.details.GameDetailsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ReviewsActivityEmptyTest {

    @Rule
    public ActivityTestRule<ReviewActivity> mActivityRule =
            new ActivityTestRule<>(ReviewActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testContentLoadedVisible(){
        launchActivity();
    }

    private void launchActivity() {
        Intent intent = new Intent(InstrumentationRegistry.getContext(), GameDetailsActivity.class);
        mActivityRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
