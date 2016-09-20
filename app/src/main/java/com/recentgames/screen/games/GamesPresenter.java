package com.recentgames.screen.games;

public class GamesPresenter {

    private GamesView mGamesView;

    public GamesPresenter(GamesView gamesView) {
        mGamesView = gamesView;
    }

    public void onMenuSearchClick() {
        mGamesView.openSearchScreen();
    }
}
