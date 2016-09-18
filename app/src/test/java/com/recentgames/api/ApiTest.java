package com.recentgames.api;

import static com.recentgames.model.QueryParams.*;
import com.recentgames.model.response.GiantBombResponse;
import com.recentgames.util.RxSchedulers;
import com.recentgames.utils.RxSchedulersHooks;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ApiTest {

    @Before
    public void setUp(){
        RxSchedulersHooks.immediate();
    }

    @Test
    public void testApiGetGame(){
        int gameId = 5;
        ApiFactory.getGiantBombService().game(gameId,GAME_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiGetGameException(){
        int gameId = 599987787;
        ApiFactory.getGiantBombService().game(gameId,GAME_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiGetGames(){
        int limit = LIMIT;
        int offset = 0;
        ApiFactory.getGiantBombService().games(GAMES_FILED_LIST,limit,offset)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiGetGamesException(){
        int limit = LIMIT;
        int offset = 999999999;
        ApiFactory.getGiantBombService().games(GAMES_FILED_LIST,limit,offset)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiGetReview(){
        int reviewId = 5;
        ApiFactory.getGiantBombService().review(reviewId, REVIEW_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiGetReviewNotFound(){
        int reviewId = 589980890;
        ApiFactory.getGiantBombService().review(reviewId, REVIEW_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiSearchGame(){
        String gameName = "Deus";
        int limit = LIMIT;
        int offset = 0;
        ApiFactory.getGiantBombService().search(gameName, REVIEW_FILED_LIST, limit,offset)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiSearchGameNotFound(){
        String gameName = "Defdsfjsdfhdsfjsdhfjsdfjus";
        int limit = LIMIT;
        int offset = 0;
        ApiFactory.getGiantBombService()
                .search(gameName, REVIEW_FILED_LIST,limit,offset)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }
}
