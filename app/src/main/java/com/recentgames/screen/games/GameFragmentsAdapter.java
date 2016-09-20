package com.recentgames.screen.games;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.recentgames.GamesType;

import java.util.ArrayList;
import java.util.List;

public class GameFragmentsAdapter  extends FragmentPagerAdapter {

    private final int PAGES_COUNT = 3;

    GameFragmentsAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == GamesType.WEEK) {
            return GamesPageFragment.newInstance(GamesType.WEEK);
        } else if (position == GamesType.MONTH) {
            return GamesPageFragment.newInstance(GamesType.MONTH);
        } else {
            return GamesPageFragment.newInstance(GamesType.YEAR);
        }
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == GamesType.WEEK) {
            return "WEEK";
        } else if (position == GamesType.MONTH) {
            return "MONTH";
        } else {
            return "YEAR";
        }
    }
}
