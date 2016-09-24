package com.recentgames.screen.gallery;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Image;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.screen.details.GameDetailsActivity;
import com.recentgames.screen.reviews.ReviewActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class GalleryActivityTest {

    @Rule
    public ActivityTestRule<GalleryActivity> mActivityRule =
            new ActivityTestRule<>(GalleryActivity.class, false, false);

    private ArrayList<Image> mImages;

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testContentLoadedVisible(){
        launchActivity();
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    private void launchActivity() {
        mImages = new ArrayList<>();
        mImages.add(new Image("http://static.giantbomb.com/uploads/screen_medium/8/82063/2879175-mankind_divided_cover.jpg"));
        mImages.add(new Image("http://static.giantbomb.com/uploads/screen_medium/0/176/2759482-041607.jpg"));
        mImages.add(new Image("http://static.giantbomb.com/uploads/screen_medium/0/176/2759481-041606.jpg"));
        mImages.add(new Image("http://static.giantbomb.com/uploads/screen_medium/0/176/2759480-041605.jpg"));
        Intent intent = new Intent(InstrumentationRegistry.getContext(), GalleryActivity.class);
        intent.putExtra(GalleryActivity.IMAGES_KEY, mImages);
        intent.putExtra(GalleryActivity.POSITION_KEY, 2);
        mActivityRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
