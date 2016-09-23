package com.recentgames.screen.games;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.recentgames.GamesType;

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
        if (position == GamesType.WEEK) {
            return "WEEK";
        } else if (position == GamesType.MONTH) {
            return "MONTH";
        } else {
            return "YEAR";
        }
    }

    private Fragment getFragment(@GamesType int position) {
        return GamesPageFragment.newInstance(position);
    }
}
