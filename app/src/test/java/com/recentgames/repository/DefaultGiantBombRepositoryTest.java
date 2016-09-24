package com.recentgames.repository;

import com.recentgames.model.content.GamePreview;
import com.recentgames.utils.RxSchedulersHooks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

@RunWith(JUnit4.class)
public class DefaultGiantBombRepositoryTest {

    private GiantBombRepository mRepository;

    @Before
    public void setUp() {
        RxSchedulersHooks.immediate();
        mRepository = RepositoryProvider.provideGiantBombRepository();
    }

    /*@Test
    public void loadGameDescription() {
        int id = 5;
        mRepository.game(id)
                .subscribe(
                        game -> {
                            Assert.assertEquals(id, game.getId());
                        },
                        Assert::assertNull
                );
    }*/

    @Test
    public void searchGameByName() {
        /*String name = "gta";
        mRepository.search(name)
                .flatMap(new Func1<List<GamePreview>, Observable<GamePreview>>() {
                    @Override
                    public Observable<GamePreview> call(List<GamePreview> gamePreviews) {
                        return Observable.from(gamePreviews);
                    }
                })
                .subscribe(Assert::assertNotNull,Assert::assertNull);
    */}
}
