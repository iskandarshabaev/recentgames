package com.recentgames.screen.games;

import com.recentgames.model.content.GamePreview;
import com.recentgames.screen.LoadingView;

import java.util.List;

public interface GamesPageView extends LoadingView {

    void getGames();

    void updateAdapter(List<GamePreview> gamePreviews);

    void showError();

}
