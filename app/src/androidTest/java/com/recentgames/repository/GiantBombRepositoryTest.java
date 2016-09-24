package com.recentgames.repository;

import android.support.test.runner.AndroidJUnit4;

import com.recentgames.api.RequestsHandler;
import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;

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
public class GiantBombRepositoryTest {

    private final static int ID = 36067;

    private GiantBombRepository mRepository;

    @Before
    public void setUp(){
        RequestsHandler.setGenerateException(false);
        mRepository = new DefaultGiantBombRepository();
    }

    @Test
    public void testGameSearch()throws Exception{
        String name = "gta";
        List<GamePreview> list = mRepository.search(name).toBlocking().first();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGameSearchNotFound()throws Exception{
        String name = "UNKNOW";
        List<GamePreview> list = mRepository.search(name).toBlocking().first();
        assertNotNull(list);
        assertEquals(0,list.size());
    }

    @After
    public void tearDown() throws Exception{
        RequestsHandler.setGenerateException(false);
    }

}
