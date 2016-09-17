package com.recentgames.screen.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.R;
import com.recentgames.router.GamesRouter;

public class SearchFragment extends Fragment {

    protected GamesRouter mGamesRouter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mGamesRouter = (GamesRouter) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement GamesRouter");
        }
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        return layout;
    }

}
