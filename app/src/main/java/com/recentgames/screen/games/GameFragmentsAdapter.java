package com.recentgames.screen.games;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.recentgames.GamesType;
import com.recentgames.model.QueryParams;

public class GameFragmentsAdapter extends FragmentPagerAdapter {

    private final int PAGES_COUNT = 3;

    GameFragmentsAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTypeName(position);
    }

    private String getTypeName(int position) {
        return QueryParams.getName(position);
    }

    private Fragment getFragment(@GamesType int position) {
        return GamesPageFragment.newInstance(position);
    }
}
