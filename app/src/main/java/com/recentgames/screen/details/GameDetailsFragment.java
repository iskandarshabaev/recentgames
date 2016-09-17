package com.recentgames.screen.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.R;

public class GameDetailsFragment extends Fragment {

    public static GameDetailsFragment newInstance(/*SomeModel data*/) {

        Bundle args = new Bundle();
        //args.putSerializable(KEY_DATA, data);

        GameDetailsFragment fragment = new GameDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_game_details, container, false);

        //SomeModel data = getArguments().getSerializable(KEY_DATA, defaultData);

        return layout;
    }

}
