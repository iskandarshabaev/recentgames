package com.recentgames.screen.details;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Image;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityEmptyTest {


    @Rule
    public ActivityTestRule<GameDetailsActivity> mActivityRule =
            new ActivityTestRule<>(GameDetailsActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testOnHomeButtonClick(){
        launchActivity();
    }

    @Test
    public void testCardsVisible(){
        launchActivity();
    }

    private void launchActivity() {
        Intent intent = new Intent(InstrumentationRegistry.getContext(),
                GameDetailsActivity.class);
        mActivityRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
