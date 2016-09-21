package com.recentgames.screen.search;

import android.content.Context;
import android.support.annotation.NonNull;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.util.RxSchedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.arturvasilov.rxloader.LifecycleHandler;

public class SearchPresenter {

    private final SearchView mSearchView;
    private final LifecycleHandler mLifecycleHandler;

    private final static int MIN_SEARCH_LENGTH = 3;
    private final static int DEBOUNCE_TIMEOUT = 300;

    public SearchPresenter(@NonNull  SearchView searchView,@NonNull LifecycleHandler lifecycleHandler) {
        mSearchView = searchView;
        mLifecycleHandler = lifecycleHandler;
    }

    private void searchGame(String name) {
        RepositoryProvider.provideGiantBombRepository()
                .search(name)
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                .doOnSubscribe(mSearchView::showLoading)
                .doAfterTerminate(mSearchView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.search_toolbar))
                .subscribe(mSearchView::showGames, throwable -> mSearchView.showError());
    }

    /*public void onGameClick(GamesRouter router, Context context, GamePreview game) {
        router.navigateFromSearchToGameDetails(context, game);
    }*/

    public void onTextChanged(String text) {
        if(text.length() == 0)
            mSearchView.clearSearchResult();
        else if(text.length() >= MIN_SEARCH_LENGTH)
            searchGame(text);
    }

    public void notifyIsNotFound(List<GamePreview> games) {
        if(games.size() == 0) {
            mSearchView.notifyIsNotFound();
        }
    }
}
