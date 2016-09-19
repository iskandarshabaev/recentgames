package com.recentgames.router;

import com.recentgames.model.content.GamePreview;

public interface GamesRouter {

    void navigateBack();

    void navigateToGames(int type);

    void navigateFromSearchToGameDetails(GamePreview gamePreview);

    void navigateFromGamesToGameDetails(GamePreview gamePreview);

    void navigateToSearch();

}
