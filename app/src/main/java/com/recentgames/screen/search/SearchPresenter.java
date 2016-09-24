package com.recentgames.screen.search;

import android.support.annotation.NonNull;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.repository.RepositoryProvider;

import java.util.List;

import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Subscription;

public class SearchPresenter {

    private final SearchView mSearchView;
    private final LifecycleHandler mLifecycleHandler;
    private Subscription mSubscription;

    private final static int MIN_SEARCH_LENGTH = 3;

    public SearchPresenter(@NonNull SearchView searchView, @NonNull LifecycleHandler lifecycleHandler) {
        mSearchView = searchView;
        mLifecycleHandler = lifecycleHandler;
    }

    public void init(String name) {
        mSubscription = RepositoryProvider.provideGiantBombRepository()
                .search(name)
                .compose(mLifecycleHandler.load(R.id.search_toolbar))
                .doOnSubscribe(mSearchView::showLoading)
                .doAfterTerminate(mSearchView::hideLoading)
                .subscribe(this::showGames, throwable -> mSearchView.showError());
    }

    private void searchGame(String name) {
        mSubscription = RepositoryProvider.provideGiantBombRepository()
                .search(name)
                .compose(mLifecycleHandler.reload(R.id.search_toolbar))
                .doOnSubscribe(mSearchView::showLoading)
                .doAfterTerminate(mSearchView::hideLoading)
                .subscribe(this::showGames, throwable -> mSearchView.showError());
    }

    public void unsubscribe() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
    }

    public void onTextChanged(String text) {
        if (text.length() == 0)
            mSearchView.clearSearchResult();
        else if (text.length() >= MIN_SEARCH_LENGTH)
            searchGame(text);
    }

    private void showGames(List<GamePreview> games) {
        if (games.size() == 0) {
            mSearchView.notifyIsNotFound();
        }

        mSearchView.showGames(games);
    }
}
