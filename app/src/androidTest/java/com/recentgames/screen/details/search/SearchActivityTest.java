package com.recentgames.screen.details.search;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import com.recentgames.R;
import com.recentgames.screen.details.GameDetailsActivity;
import com.recentgames.screen.search.SearchActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by rodionov on 22.09.2016.
 */

@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    @Rule
    public final ActivityTestRule<SearchActivity> mActivityRule = new ActivityTestRule<>(SearchActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testSearchView() throws Exception {

        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("gta"), pressKey(KeyEvent.KEYCODE_ENTER));
        closeSoftKeyboard();
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(15))
                .perform(scrollToPosition(8))
                .perform(scrollToPosition(1))
                .perform(scrollToPosition(2))
                .perform(scrollToPosition(10))
                .perform(scrollToPosition(19));

        /*onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(14, click()));

        Intents.intended(hasComponent(GameDetailsActivity.class.getName()));*/

        onView(withId(android.support.design.R.id.search_close_btn)).perform(click());
        onView(withId(android.support.design.R.id.search_close_btn)).check(matches(allOf(
                withEffectiveVisibility(ViewMatchers.Visibility.GONE)
        )));
    }

    @Test
    public void testInvisibleProgressBar() throws Exception {
        onView(withId(R.id.progress)).check(matches(allOf(
                withEffectiveVisibility(ViewMatchers.Visibility.GONE)
        )));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}
