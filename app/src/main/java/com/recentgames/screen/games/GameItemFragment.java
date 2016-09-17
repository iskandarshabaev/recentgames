package com.recentgames.screen.games;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.R;
import com.recentgames.router.GamesRouter;

public class GameItemFragment extends Fragment {

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

    private static final String KEY_TYPE = "KEY_TYPE";

    public static GameItemFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);

        GameItemFragment fragment = new GameItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_game_item, container, false);
        return layout;
    }

}
