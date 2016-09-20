package com.recentgames.router.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.OnSearchStateChanged;
import com.recentgames.screen.details.GameDetailsActivity;
import com.recentgames.screen.details.GameDetailsFragment;
import com.recentgames.screen.games.GamesFragment;
import com.recentgames.screen.search.SearchFragment;

public class GamesRouterImpl implements GamesRouter {

    private final FragmentManager mFragmentManager;

    public GamesRouterImpl(@NonNull FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Override
    public void navigateBack() {
        mFragmentManager.popBackStack();
    }

    @Override
    public void navigateToGames(int type) {
        mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                //.addToBackStack(null)
                .replace(R.id.fragment_container, GamesFragment.newInstance())
                .commit();

    }

    @Override
    public void navigateFromSearchToGameDetails(Context context, GamePreview gamePreview) {
        /*mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                //.addToBackStack(null)
                .replace(R.id.fragment_container, GameDetailsFragment.newInstance(gamePreview))
                .commit();*/
        GameDetailsActivity.showActivity(context, gamePreview);
    }

    @Override
    public void navigateFromGamesToGameDetails(Context context, GamePreview gamePreview) {
        /*mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(R.id.fragment_container, GameDetailsFragment.newInstance(gamePreview))
                .commit();*/

            GameDetailsActivity.showActivity(context, gamePreview);
    }

    @Override
    public void navigateFromGamesDetailsToGameDetails(Context context, GamePreview gamePreview) {
        /*mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, GameDetailsFragment.newInstance(gamePreview))
                .commit();*/
        GameDetailsActivity.showActivity(context, gamePreview);
    }

    @Override
    public void navigateToSearch() {
        mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .add(R.id.fragment_container, SearchFragment.newInstance())
                .commit();
    }

    public void addSearchStateListener(OnSearchStateChanged onSearchStateChanged) {
        mFragmentManager.addOnBackStackChangedListener(() -> {
            if (onSearchStateChanged == null) return;
            if (mFragmentManager.getBackStackEntryCount() == 0) {
                onSearchStateChanged.onSearchRemoved();
            } else {
                onSearchStateChanged.onSearchAdded();
            }
        });
    }

}
