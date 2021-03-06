package com.recentgames.repository.details;

import android.support.test.runner.AndroidJUnit4;

import com.recentgames.api.RequestsHandler;
import com.recentgames.model.content.GameDescription;
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
public class GameDescriptionGiantBombRepositoryTest {

    private final static int ID = 36067;

    private GiantBombRepository mRepository;

    @Before
    public void setUp(){
        RequestsHandler.setGenerateException(false);
        mRepository = new DefaultGiantBombRepository();
    }

    @Test
    public void testGameDescriptionLoad()throws Exception{
        int id = ID;
        GameDescription gameDescription = mRepository.game(id).toBlocking().first();
        assertNotNull(gameDescription);
        assertEquals(gameDescription.getId(), id);
    }

    @Test
    public void testGameDescriptionSaved()throws Exception{
        RequestsHandler.setGenerateException(false);
        int id = ID;
        mRepository.game(id).subscribe();
        GameDescription gameDescription = Realm.getDefaultInstance()
                .where(GameDescription.class)
                .equalTo("mId", id)
                .findFirst();
        assertNotNull(gameDescription);
        assertEquals(gameDescription.getId(), id);
    }

    @Test
    public void testGameDescriptionNotFound()throws Exception{
        int id = 999778769;
        TestSubscriber<GameDescription> testSubscriber = new TestSubscriber<>();
        mRepository.game(id).subscribe(testSubscriber);
        testSubscriber.assertNotCompleted();
    }

    @Test
    public void testGameDescriptionLoadedFromCache()throws Exception{
        int id = ID;
        mRepository.game(id).subscribe();
        RequestsHandler.setGenerateException(true);

        TestSubscriber<GameDescription> testSubscriber = new TestSubscriber<>();
        mRepository.game(id).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        RequestsHandler.setGenerateException(false);
    }

    @After
    public void tearDown() throws Exception{
        RequestsHandler.setGenerateException(false);
    }

}
