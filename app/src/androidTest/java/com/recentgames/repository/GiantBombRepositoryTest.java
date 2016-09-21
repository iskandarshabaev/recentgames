package com.recentgames.repository;

import android.support.test.runner.AndroidJUnit4;

import com.recentgames.model.content.GameDescription;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class GiantBombRepositoryTest {

    private GiantBombRepository mRepository;

    @Before
    public void setUp(){
        mRepository = new DefaultGiantBombRepository();
    }

    @Test
    public void testGameDescriptionLoad()throws Exception{
        int id = 36067;
        GameDescription gameDescription = mRepository.game(id).toBlocking().first();
        assertNotNull(gameDescription);
        assertEquals(gameDescription.getId(), id);
    }

    @Test
    public void testGameDescriptionSaved()throws Exception{
        int id = 36067;
        mRepository.game(id).subscribe();
        GameDescription gameDescription = Realm.getDefaultInstance()
                .where(GameDescription.class)
                .equalTo("mId", id)
                .findFirst();
        assertNotNull(gameDescription);
        assertEquals(gameDescription.getId(), id);
    }

}
