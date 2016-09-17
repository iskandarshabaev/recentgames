package com.recentgames.screen.games;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.recentgames.GamesType;

import java.util.ArrayList;
import java.util.List;

public class GameFragmentsAdapter  extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentsList = new ArrayList<>();

    GameFragmentsAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }

    void addFragment(Fragment fragment) {
        mFragmentsList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        mFragmentsList.get(position);
        if (position == GamesType.WEEK) {
            return "WEEK";
        } else if (position == GamesType.MONTH) {
            return "MONTH";
        } else {
            return "YEAR";
        }
    }
}
