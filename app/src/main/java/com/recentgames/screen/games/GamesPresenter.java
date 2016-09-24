package com.recentgames.screen.games;

import android.support.annotation.NonNull;

class GamesPresenter {

    private GamesView mGamesView;

    GamesPresenter(@NonNull GamesView gamesView) {
        mGamesView = gamesView;
    }

    void onMenuSearchClick() {
        mGamesView.openSearchScreen();
    }
}
