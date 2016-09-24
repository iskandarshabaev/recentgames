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
public class DetailsActivityTest {

    private GamePreview mGame;

    @Rule
    public ActivityTestRule<GameDetailsActivity> mActivityRule =
            new ActivityTestRule<>(GameDetailsActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testOnHomeButtonClick(){
        launchActivity(36067);
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @Test
    public void testCardsVisible(){
        launchActivity(36067);
        onView(withId(R.id.description_card)).check(matches(isDisplayed()));
        onView(withId(R.id.game_name)).check(matches(withText(mGame.getName())));
        onView(withId(R.id.poster)).perform(swipeUp());
        onView(withId(R.id.images_card)).check(matches(isDisplayed()));
        onView(withId(R.id.description_card)).perform(swipeUp());
    }

    @Test
    public void testErrorShow(){
        launchActivity(99999999);
    }

    private void launchActivity(int id) {
        Image image = new Image("http://www.giantbomb.com/api/image/scale_medium/2669576-destiny%20v2.jpg");
        mGame = new GamePreview(id, image, "Destiny",new Date());
        Intent intent = new Intent(InstrumentationRegistry.getContext(), GameDetailsActivity.class);
        intent.putExtra(GameDetailsActivity.GAME_PREVIEW_KEY, mGame);
        mActivityRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
