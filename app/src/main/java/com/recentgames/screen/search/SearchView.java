package com.recentgames.screen.search;

import com.recentgames.model.content.GamePreview;
import com.recentgames.screen.LoadingView;

import java.util.List;

public interface SearchView extends LoadingView {

    void showGames(List<GamePreview> list);

    void showError();

    void clearSearchResult();

    void notifyIsNotFound();
}