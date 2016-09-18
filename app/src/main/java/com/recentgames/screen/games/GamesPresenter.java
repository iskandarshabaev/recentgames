package com.recentgames.screen.games;

public class GamesPresenter {

    private GamesView mGamesView;

    public GamesPresenter(GamesView gamesView) {
        mGamesView = gamesView;
    }

    public void onSearchRemoved() {
        mGamesView.expandAppBar();
    }

    public void onSearchAdded() {
        mGamesView.collapseAppBar();
    }

    public void onMenuSearchClick() {
        mGamesView.openSearchFragment();
    }
}
