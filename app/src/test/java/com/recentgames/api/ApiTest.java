package com.recentgames.api;

import com.recentgames.model.QueryParams;
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
        ApiFactory.getGiantBombService().game(5, QueryParams.GAME_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiGetGameException(){
        ApiFactory.getGiantBombService().game(599987787, QueryParams.GAME_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiGetGames(){
        ApiFactory.getGiantBombService().games(QueryParams.GAMES_FILED_LIST,5,0)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiGetGamesException(){
        ApiFactory.getGiantBombService().games(QueryParams.GAMES_FILED_LIST, 5, 999999999)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiGetReview(){
        ApiFactory.getGiantBombService().review(5,QueryParams.REVIEW_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiGetReviewNotFound(){
        ApiFactory.getGiantBombService().review(589980890,QueryParams.REVIEW_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiSearchGame(){
        ApiFactory.getGiantBombService().search("Deus",
                QueryParams.REVIEW_FILED_LIST,
                QueryParams.LIMIT,0)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiSearchGameNotFound(){
        ApiFactory.getGiantBombService()
                .search("Defdsfjsdfhdsfjsdhfjsdfjus",
                        QueryParams.REVIEW_FILED_LIST,
                        QueryParams.LIMIT,0)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }
}
