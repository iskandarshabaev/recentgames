package com.recentgames.router;

import com.recentgames.model.content.GamePreview;

public interface GamesRouter {

    void navigateBack();

    void navigateToGames(int type);

    void navigateFromSearchToGameDetails(GamePreview game);

    void navigateFromGamesToGameDetails();

    void navigateToSearch();

}
