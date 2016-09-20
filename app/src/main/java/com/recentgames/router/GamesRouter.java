package com.recentgames.router;

import android.content.Context;

import com.recentgames.model.content.GamePreview;

public interface GamesRouter {

    void navigateBack();

    void navigateToGames(int type);

    void navigateFromSearchToGameDetails(Context context, GamePreview gamePreview);

    void navigateFromGamesToGameDetails(Context context, GamePreview gamePreview);

    void navigateFromGamesDetailsToGameDetails(Context context, GamePreview gamePreview);

    void navigateToSearch();

    void addSearchStateListener(OnSearchStateChanged onSearchStateChanged);

}
