package com.recentgames.router.impl;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.recentgames.R;
import com.recentgames.router.GamesRouter;
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
    public void navigateFromSearchToGameDetails() {
        mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                //.addToBackStack(null)
                .replace(R.id.fragment_container, GameDetailsFragment.newInstance())
                .commit();
    }

    @Override
    public void navigateFromGamesToGameDetails() {
        mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(R.id.fragment_container, GameDetailsFragment.newInstance())
                .commit();
    }

    @Override
    public void navigateToSearch() {
        mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(R.id.fragment_container, SearchFragment.newInstance())
                .commit();
    }

}
