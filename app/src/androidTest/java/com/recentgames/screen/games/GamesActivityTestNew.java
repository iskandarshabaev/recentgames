package com.recentgames.screen.games;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.recentgames.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GamesActivityTestNew {

    @Rule
    public ActivityTestRule<GamesActivity> mActivityTestRule = new ActivityTestRule<>(GamesActivity.class);

    @Test
    public void gamesActivityTestNew() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_search), withContentDescription("Search"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.menu_search), withContentDescription("Search"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("gtta"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(R.id.search_src_text), withText("gtta"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete2.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.search_close_btn), withContentDescription("Удалить запрос"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction searchAutoComplete3 = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete3.perform(replaceText("gta"), closeSoftKeyboard());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        pressBack();

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.search_close_btn), withContentDescription("Удалить запрос"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        appCompatImageView2.perform(click());

    }

}
