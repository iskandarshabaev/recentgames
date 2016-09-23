package com.recentgames.screen.games;

import android.support.annotation.NonNull;

import com.recentgames.exception.LimitReachedException;
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
        //if (/*offset % QueryParams.LIMIT_COUNT != 0 || */mIsCache) mGamesPageView.deactivateBottomRefresh(); //means that we have no more games (or just we don't wanna show their, lol)
        RepositoryProvider.provideGiantBombRepository()
                .games(type, offset)
                .compose(offset == 0 ? mLifecycleHandler.load(QueryParams.getLoaderId(type)) : mLifecycleHandler.add(QueryParams.getLoaderId(type)))
                .doOnSubscribe(offset == 0 ? mShowLoading : mShowRefreshing)
                .doOnTerminate(offset == 0 ? mHideLoading : mHideRefreshing)
                .subscribe(gamePreviews -> {
                    if (gamePreviews.isCached()) mGamesPageView.deactivateBottomRefresh();
                    mGamesPageView.updateAdapter(gamePreviews.getGamePreviews());
                }, throwable -> {
                    throwable.printStackTrace();
                    if (throwable instanceof LimitReachedException) {
                        mGamesPageView.deactivateBottomRefresh();
                    } else {
                        mGamesPageView.showError();
                    }
                });
    }

    public void refreshGames(int type) {
        int offset = 0;
        RepositoryProvider.provideGiantBombRepository()
                .games(type, offset)
                .compose(mLifecycleHandler.reload(QueryParams.getLoaderId(type)))
                .doOnTerminate(mHideRefreshing)
                .subscribe(gamePreviews -> {
                    if (gamePreviews.isCached()) mGamesPageView.deactivateBottomRefresh();
                    mGamesPageView.changeGames(gamePreviews.getGamePreviews());
                    mGamesPageView.activateBottomRefresh();
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

    private Action0 mShowRefreshing = new Action0() {
        @Override
        public void call() {
            mGamesPageView.showRefreshing();
        }
    };

    private Action0 mHideLoading = new Action0() {
        @Override
        public void call() {
            mGamesPageView.hideLoading();
        }
    };

    private Action0 mHideRefreshing = new Action0() {
        @Override
        public void call() {
            mGamesPageView.hideRefreshing();
        }
    };
}
