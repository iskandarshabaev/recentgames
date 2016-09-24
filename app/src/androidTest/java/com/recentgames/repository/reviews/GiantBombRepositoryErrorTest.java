package com.recentgames.repository.reviews;

import android.support.test.runner.AndroidJUnit4;

import com.recentgames.api.RequestsHandler;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.repository.DefaultGiantBombRepository;
import com.recentgames.repository.GiantBombRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class GiantBombRepositoryErrorTest {

    private GiantBombRepository mRepository;

    @Before
    public void setUp(){
        RequestsHandler.setGenerateException(false);
        mRepository = new DefaultGiantBombRepository();
    }

    @Test
    public void testReviewNotFound()throws Exception{
        int id = 999778769;
        TestSubscriber<ReviewDescription> testSubscriber = new TestSubscriber<>();
        mRepository.review(id).subscribe(testSubscriber);
        testSubscriber.assertNotCompleted();
    }

    @Test
    public void testReviewLoadedFromCache()throws Exception{
        int id = 45;
        mRepository.review(id).subscribe();
        RequestsHandler.setGenerateException(true);

        TestSubscriber<ReviewDescription> testSubscriber = new TestSubscriber<>();
        mRepository.review(id).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        RequestsHandler.setGenerateException(false);
    }


    @After
    public void tearDown() throws Exception{
        RequestsHandler.setGenerateException(false);
    }

}
