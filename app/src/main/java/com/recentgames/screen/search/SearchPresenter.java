package com.recentgames.screen.search;

import android.support.annotation.NonNull;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.router.GamesRouter;
import com.recentgames.util.RxSchedulers;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ru.arturvasilov.rxloader.LifecycleHandler;

public class SearchPresenter {

    private final SearchView mSearchView;
    private final LifecycleHandler mLifecycleHandler;

    private final static int MIN_SEARCH_LENGTH = 3;

    public SearchPresenter(@NonNull  SearchView searchView,@NonNull LifecycleHandler lifecycleHandler) {
        mSearchView = searchView;
        mLifecycleHandler = lifecycleHandler;
    }

    public void searchGame(String name) {
        RepositoryProvider.provideGiantBombRepository()
                .search(name)
                .debounce(300, TimeUnit.MILLISECONDS)
                .compose(mLifecycleHandler.reload(R.id.search_toolbar))
                .compose(RxSchedulers.async())
                .subscribe(mSearchView::showGames, throwable -> mSearchView.showError());
    }

    public void onGameClick(GamesRouter router, GamePreview game) {
        router.navigateFromSearchToGameDetails(game);
    }

    public void onTextChanged(String text) {
        if(text.length() == 0)
            mSearchView.showGames(new ArrayList<>());
        else if(text.length() >= MIN_SEARCH_LENGTH)
            searchGame(text);
    }
}
