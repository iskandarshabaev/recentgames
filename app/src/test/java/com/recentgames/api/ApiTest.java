package com.recentgames.api;

import com.recentgames.model.response.GiantBombResponse;
import com.recentgames.util.RxSchedulers;
import com.recentgames.utils.RxSchedulersHooks;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static com.recentgames.model.QueryParams.*;

public class ApiTest {

    @Before
    public void setUp() {
        RxSchedulersHooks.immediate();
    }

    @Test
    public void testApiGetGame() {
        int gameId = 5;
        ApiFactory.getGiantBombService().game(gameId, GAME_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiGetGameException() {
        int invalidGameId = 599987787;
        ApiFactory.getGiantBombService().game(invalidGameId, GAME_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiGetWeekGames() {
        int limit = LIMIT_COUNT;
        int offset = 0;
        //String sort = QueryParams.GAME_SORT_BY_REVIEWS_COUNT;
        String filter = getFilter(GamesType.WEEK);
        ApiFactory.getGiantBombService().games(GAMES_FILED_LIST, filter,/* sort,*/ limit, offset)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiGetGamesException() {
        int limit = LIMIT_COUNT;
        int invalidOffset = 999999999;
        //String sort = QueryParams.GAME_SORT_BY_REVIEWS_COUNT;
        String filter = getFilter(GamesType.WEEK);
        ApiFactory.getGiantBombService().games(GAMES_FILED_LIST, filter,/* sort,*/ limit, invalidOffset)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiGetReview() {
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
    public void testApiGetReviewNotFound() {
        int invalidReviewId = 589980890;
        ApiFactory.getGiantBombService().review(invalidReviewId, REVIEW_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }

    @Test
    public void testApiSearchGame() {
        String gameName = "Deus";
        int limit = LIMIT_COUNT;
        ApiFactory.getGiantBombService().search(gameName, REVIEW_FILED_LIST, limit, RESOURCES)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNotNull,
                        Assert::assertNull
                );
    }

    @Test
    public void testApiSearchGameNotFound() {
        String invalidGameName = "Defdsfjsdfhdsfjsdhfjsdfjus";
        int limit = LIMIT_COUNT;
        ApiFactory.getGiantBombService()
                .search(invalidGameName, REVIEW_FILED_LIST, limit, RESOURCES)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .subscribe(
                        Assert::assertNull,
                        Assert::assertNotNull
                );
    }
}