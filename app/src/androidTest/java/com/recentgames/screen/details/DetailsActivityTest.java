package com.recentgames.screen.details;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.NestedScrollView;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Image;
import com.recentgames.screen.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    private GamePreview mGame;

    @Rule
    public ActivityTestRule<GameDetailsActivity> mActivityRule =
            new ActivityTestRule<>(GameDetailsActivity.class, false, false);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testCardsVisible(){
        launchActivity();
        onView(withId(R.id.description_card)).check(matches(isDisplayed()));
        onView(withId(R.id.game_name)).check(matches(withText(mGame.getName())));
        onView(withId(R.id.poster)).perform(swipeUp());
        onView(withId(R.id.images_card)).check(matches(isDisplayed()));
        onView(withId(R.id.description_card)).perform(swipeUp());
    }

    private void launchActivity() {
        Image image = new Image("http://www.giantbomb.com/api/image/scale_medium/2669576-destiny%20v2.jpg");
        mGame = new GamePreview(36067, image, "Destiny");
        Intent intent = new Intent(InstrumentationRegistry.getContext(), GameDetailsActivity.class);
        intent.putExtra(GameDetailsActivity.GAME_PREVIEW_KEY, mGame);
        mActivityRule.launchActivity(intent);
    }

}
