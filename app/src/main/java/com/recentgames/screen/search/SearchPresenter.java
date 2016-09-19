package com.recentgames.screen.search;

import android.support.annotation.NonNull;

import com.recentgames.R;
import com.recentgames.repository.RepositoryProvider;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenter {

    private final SearchView mSearchView;
    private final LifecycleHandler mLifecycleHandler;

    public SearchPresenter(@NonNull  SearchView searchView,@NonNull LifecycleHandler lifecycleHandler) {
        mSearchView = searchView;
        mLifecycleHandler = lifecycleHandler;
    }

    public void searchGame(String name) {
        RepositoryProvider.provideGiantBombRepository()
                .search(name)
                .debounce(500, TimeUnit.MILLISECONDS)
                .doOnSubscribe(mSearchView::showLoading)
                .doAfterTerminate(mSearchView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.search_toolbar))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSearchView::showGames, throwable -> mSearchView.showError());
    }

    public void clear(){
        mSearchView.showGames(new ArrayList<>());
    }
}
