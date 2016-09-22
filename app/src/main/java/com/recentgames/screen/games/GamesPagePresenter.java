package com.recentgames.screen.games;

import android.support.annotation.NonNull;

import com.recentgames.model.QueryParams;
import com.recentgames.model.content.GamePreview;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.rxloader.GamePreviewLifecycleHandler;

import rx.functions.Action0;

public class GamesPagePresenter {

    private final GamePreviewLifecycleHandler mLifecycleHandler;
    private GamesPageView mGamesPageView;

    public GamesPagePresenter(@NonNull GamePreviewLifecycleHandler lifecycleHandler,
                              GamesPageView gamesPageView) {
        mLifecycleHandler = lifecycleHandler;
        mGamesPageView = gamesPageView;
    }

    public void onItemClick(GamePreview gamePreview) {
        mGamesPageView.openGameDetailsScreen(gamePreview);
    }

    public void getGames(int type, int offset) {
        if (offset % QueryParams.LIMIT_COUNT != 0) return; //means that we have no more games
        RepositoryProvider.provideGiantBombRepository()
                .games(type, offset)
                .compose(offset == 0 ? mLifecycleHandler.load(QueryParams.getLoaderId(type)) : mLifecycleHandler.add(QueryParams.getLoaderId(type)))
                .doOnSubscribe(offset == 0 ? mShowLoading : mShowRecyclerLoading)
                .doOnTerminate(offset == 0 ? mHideLoading : mHideRecyclerLoading)
                .subscribe(gamePreviews -> {
                    mGamesPageView.updateAdapter(gamePreviews);
                }, throwable -> {
                    throwable.printStackTrace();
                    mGamesPageView.showError();
                });
    }

    private Action0 mShowLoading = new Action0() {
        @Override
        public void call() {
            mGamesPageView.showLoading();
        }
    };

    private Action0 mShowRecyclerLoading = new Action0() {
        @Override
        public void call() {
            mGamesPageView.showRecyclerLoading();
        }
    };

    private Action0 mHideLoading = new Action0() {
        @Override
        public void call() {
            mGamesPageView.hideLoading();
        }
    };

    private Action0 mHideRecyclerLoading = new Action0() {
        @Override
        public void call() {
            mGamesPageView.hideRecyclerLoading();
        }
    };
}
