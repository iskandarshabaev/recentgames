package com.recentgames.repository.reviews;

import android.support.test.runner.AndroidJUnit4;

import com.recentgames.api.RequestsHandler;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.repository.DefaultGiantBombRepository;
import com.recentgames.repository.GiantBombRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.realm.Realm;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ReviewsGiantBombRepositoryTest {

    private GiantBombRepository mRepository;

    @Before
    public void setUp(){
        RequestsHandler.setGenerateException(false);
        mRepository = new DefaultGiantBombRepository();
    }

    @Test
    public void testReviewSaved()throws Exception{
        int id = 45;
        ReviewDescription reviewDescription =
                mRepository.review(id).toBlocking().first();
        ReviewDescription reviewDesrctiption = Realm.getDefaultInstance()
                .where(ReviewDescription.class)
                .equalTo("mId", id)
                .findFirst();
        ReviewDescription result = Realm.getDefaultInstance()
                .copyFromRealm(reviewDesrctiption);
        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @After
    public void tearDown() throws Exception{
        RequestsHandler.setGenerateException(false);
    }

}
