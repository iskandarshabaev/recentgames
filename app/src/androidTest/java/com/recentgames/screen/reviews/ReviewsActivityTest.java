package com.recentgames.screen.reviews;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.screen.details.GameDetailsActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.recentgames.action.WaitAction.waitId;

@RunWith(AndroidJUnit4.class)
public class ReviewsActivityTest {

    private ReviewPreview mReview;

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
        onView(withId(R.id.deck)).check(matches(isDisplayed()));
        onView(withId(R.id.author_name)).check(matches(isDisplayed()));
        onView(withId(R.id.publishDate)).check(matches(isDisplayed()));
        onView(withId(R.id.deck)).perform(swipeUp());
        onView(withId(R.id.content)).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitId(R.id.content, 6000));
    }

    @Test
    public void testOnHomeButtonClick(){
        launchActivity();
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    private void launchActivity() {
        mReview = new ReviewPreview(45, "review name");
        Intent intent = new Intent(InstrumentationRegistry.getContext(), GameDetailsActivity.class);
        intent.putExtra(ReviewActivity.REVIEW_KEY, mReview);
        mActivityRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
