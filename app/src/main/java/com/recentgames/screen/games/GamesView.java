package com.recentgames.screen.games;

import com.recentgames.screen.LoadingView;

public interface GamesView extends LoadingView {

    void collapseAppBar();
    void expandAppBar();
    void openSearchFragment();
}