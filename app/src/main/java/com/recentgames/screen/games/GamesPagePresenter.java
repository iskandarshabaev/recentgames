package com.recentgames.screen.games;

import android.support.annotation.NonNull;

import com.recentgames.GamesType;
import com.recentgames.R;
import com.recentgames.api.ApiFactory;
import com.recentgames.model.QueryParams;
import com.recentgames.model.response.GiantBombResponse;
import com.recentgames.util.RxSchedulers;

import ru.arturvasilov.rxloader.LifecycleHandler;

public class GamesPagePresenter {

    private final LifecycleHandler mLifecycleHandler;
    private GamesPageView mGamesPageView;

    public GamesPagePresenter(@NonNull LifecycleHandler lifecycleHandler,
                              GamesPageView gamesPageView) {
        mLifecycleHandler = lifecycleHandler;
        mGamesPageView = gamesPageView;
    }

    public void getGames(int type) {

        int id;
        if (type == GamesType.WEEK) {
            id = R.id.get_games_week;
        } else if (type == GamesType.MONTH) {
            id = R.id.get_games_month;
        } else if (type == GamesType.YEAR) {
            id = R.id.get_games_year;
        } else {
            return;
        }

        int offset = 0;
        ApiFactory.getGiantBombService()
                .games(
                        QueryParams.GAMES_FILED_LIST,
                        QueryParams.getWeekFilter(),
                        QueryParams.GAME_SORT_BY_REVIEWS_COUNT,
                        QueryParams.LIMIT_COUNT,
                        offset)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async())
                .compose(mLifecycleHandler.load(id))
                .subscribe(gamePreviews -> {
                    mGamesPageView.updateAdapter(gamePreviews);
                }, throwable -> {
                    throwable.printStackTrace();
                    mGamesPageView.showError();
                });
    }


}
