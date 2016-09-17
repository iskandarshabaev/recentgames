package com.recentgames.screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.recentgames.GamesType;
import com.recentgames.R;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.impl.GamesRouterImpl;

public class MainActivity extends AppCompatActivity implements GamesRouter {

    private GamesRouterImpl mGamesRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGamesRouter = new GamesRouterImpl(getSupportFragmentManager());
        mGamesRouter.navigateToGames(GamesType.WEEK);
    }

    @Override
    public void navigateToGames(int type) {
        mGamesRouter.navigateToGames(type);
    }

    @Override
    public void navigateFromSearchToGameDetails() {
        mGamesRouter.navigateFromSearchToGameDetails();
    }

    @Override
    public void navigateFromGamesToGameDetails() {
        mGamesRouter.navigateFromGamesToGameDetails();
    }

    @Override
    public void navigateToSearch() {
        mGamesRouter.navigateToSearch();
    }
}
