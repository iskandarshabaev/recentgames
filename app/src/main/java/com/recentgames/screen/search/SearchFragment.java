package com.recentgames.screen.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.R;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.impl.GamesRouterImpl;

public class SearchFragment extends Fragment {

    protected GamesRouter mGamesRouter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        mGamesRouter = new GamesRouterImpl(getActivity().getSupportFragmentManager());
        return layout;
    }

}
