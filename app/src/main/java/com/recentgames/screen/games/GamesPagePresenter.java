package com.recentgames.screen.games;

import android.support.annotation.NonNull;

import com.recentgames.exception.LimitReachedException;
import com.recentgames.model.QueryParams;
import com.recentgames.model.content.GamePreview;
import com.recentgames.repository.GiantBombRepository;
import com.recentgames.rxloader.GamePreviewLifecycleHandler;

class GamesPagePresenter {

    private GamesPageView mGamesPageView;
    private final GamePreviewLifecycleHandler mLifecycleHandler;
    private GiantBombRepository mRepository;

    GamesPagePresenter(@NonNull GamesPageView gamesPageView,
                              @NonNull GamePreviewLifecycleHandler lifecycleHandler,
                       @NonNull GiantBombRepository giantBombRepository) {
        mGamesPageView = gamesPageView;
        mLifecycleHandler = lifecycleHandler;
        mRepository = giantBombRepository;
    }

    void init(int type) {
        int offset = 0;
        getGames(type, offset);
    }

    void onItemClick(@NonNull GamePreview gamePreview) {
        mGamesPageView.hideRefreshing();
        mGamesPageView.hideLoading();
        mGamesPageView.openGameDetailsScreen(gamePreview);
    }

    void onSnackbarClick(int type) {
        mGamesPageView.showRefreshing();
        refreshGames(type);
    }

    void getGames(int type, int offset) {
        mRepository
                .games(type, offset)
                .doOnSubscribe(() -> showLoading(offset))
                .compose(offset == 0 ? mLifecycleHandler.load(QueryParams.getLoaderId(type)) : mLifecycleHandler.add(QueryParams.getLoaderId(type)))
                .subscribe(gamePreviews -> {
                    if (gamePreviews.isCached()) mGamesPageView.deactivateBottomRefresh();
                    mGamesPageView.updateAdapter(gamePreviews.getGamePreviews());
                    hideLoading(offset);
                }, throwable -> {
                    throwable.printStackTrace();
                    if (throwable instanceof LimitReachedException) {
                        mGamesPageView.deactivateBottomRefresh();
                    } else {
                        mGamesPageView.showError();
                    }
                    hideLoading(offset);
                });
    }

    void refreshGames(int type) {
        int offset = 0;
        mRepository
                .games(type, offset)
                .compose(mLifecycleHandler.reload(QueryParams.getLoaderId(type)))
                .subscribe(gamePreviews -> {
                    if (gamePreviews.isCached()) {
                        mGamesPageView.deactivateBottomRefresh();
                    } else {
                        mGamesPageView.activateBottomRefresh();
                    }
                    mGamesPageView.changeGames(gamePreviews.getGamePreviews());
                    hideRefreshing();
                }, throwable -> {
                    mGamesPageView.showErrorWithRetry();
                    hideRefreshing();
                });
    }

    private void showLoading() {
        mGamesPageView.showLoading();
    }

    private void hideLoading() {
        mGamesPageView.hideLoading();
    }

    private void showLoading(int offset) {
        if (offset == 0) {
            showLoading();
        } else {
            showRefreshing();
        }
    }

    private void hideLoading (int offset) {
        if (offset == 0) {
            hideLoading();
        } else {
            hideRefreshing();
        }
    }

    private void showRefreshing() {
        mGamesPageView.showRefreshing();
    }

    private void hideRefreshing() {
        mGamesPageView.hideRefreshing();
    }
}
