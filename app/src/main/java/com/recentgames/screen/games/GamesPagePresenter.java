package com.recentgames.screen.games;

import android.support.annotation.NonNull;

import com.recentgames.model.QueryParams;
import com.recentgames.repository.RepositoryProvider;

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
        int offset = 0;
        RepositoryProvider.provideGiantBombRepository()
                .games(type, offset)
                .compose(mLifecycleHandler.load(QueryParams.getLoaderId(type)))
                .doOnSubscribe(mGamesPageView::showLoading)
                .doOnTerminate(mGamesPageView::hideLoading)
                .subscribe(gamePreviews -> {
                    mGamesPageView.updateAdapter(gamePreviews);
                }, throwable -> {
                    throwable.printStackTrace();
                    mGamesPageView.showError();
                });
    }
}
