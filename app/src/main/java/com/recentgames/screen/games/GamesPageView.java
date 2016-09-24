package com.recentgames.screen.games;

import com.recentgames.model.content.GamePreview;
import com.recentgames.screen.LoadingView;

import java.util.List;

interface GamesPageView extends LoadingView {

    void updateAdapter(List<GamePreview> gamePreviews);

    void changeGames(List<GamePreview> gamePreviews);

    void showError();

    void showErrorWithRetry();

    void showRefreshing();

    void hideRefreshing();

    void deactivateBottomRefresh();

    void activateBottomRefresh();

    void openGameDetailsScreen(GamePreview gamePreview);

}
