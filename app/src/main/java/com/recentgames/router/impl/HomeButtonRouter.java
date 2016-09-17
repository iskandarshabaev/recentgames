package com.recentgames.router.impl;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

public class HomeButtonRouter {

    private final FragmentManager mFragmentManager;

    public HomeButtonRouter(@NonNull FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void onHomeButtonClicked() {
        mFragmentManager.popBackStack();
    }
}
