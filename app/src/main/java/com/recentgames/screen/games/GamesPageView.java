package com.recentgames.screen.games;

import com.recentgames.model.content.GamePreview;
import com.recentgames.screen.LoadingView;

import java.util.List;

public interface GamesPageView extends LoadingView {

    void updateAdapter(List<GamePreview> gamePreviews);

    void changeGames(List<GamePreview> gamePreviews);

    void showError();

    void showRefreshing();

    void hideRefreshing();

    void deactivateBottomRefresh();

    void activateBottomRefresh();

    void openGameDetailsScreen(GamePreview gamePreview);

}
