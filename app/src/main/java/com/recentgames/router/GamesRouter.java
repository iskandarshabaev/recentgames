package com.recentgames.router;

public interface GamesRouter {

    void navigateBack();

    void navigateToGames(int type);

    void navigateFromSearchToGameDetails();

    void navigateFromGamesToGameDetails();

    void navigateToSearch();

    void addSearchStateListener(OnSearchStateChanged onSearchStateChanged);

}
