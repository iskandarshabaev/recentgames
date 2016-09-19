package com.recentgames.repository;

import com.recentgames.utils.RxSchedulersHooks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Iskandar on 18.09.2016.
 */
public class DefaultGiantBombRepositoryTest {

    private GiantBombRepository mRepository;

    @Before
    public void setUp(){
        RxSchedulersHooks.immediate();
        mRepository = RepositoryProvider.provideGiantBombRepository();
    }

    @Test
    public void loadGameDescription(){
        int id = 5;
        mRepository.game(id)
                .subscribe(
                        game ->{
                            Assert.assertEquals(id, game.getId());
                        },
                        Assert::assertNull
                );
    }
}
