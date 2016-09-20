package com.recentgames.screen.games;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.impl.GamesRouterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class GamesPageFragment extends Fragment implements GamesPageView {

    @BindView(R.id.games_page_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.games_page_recycler)
    RecyclerView mGamesPageRecyclerView;

    protected GamesRouter mGamesRouter;

    private GamesPagePresenter mGamesPagePresenter;
    private GamesPageAdapter mGamesAdapter;

    private static final String KEY_TYPE = "KEY_TYPE";
    private static final int TYPE_DEFAULT = -1;
    private int type;

    public static GamesPageFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);

        GamesPageFragment fragment = new GamesPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_games_page, container, false);
        ButterKnife.bind(this, layout);
        type = getArguments().getInt(KEY_TYPE, TYPE_DEFAULT);
        mGamesRouter = new GamesRouterImpl(getActivity().getSupportFragmentManager());

        mGamesAdapter = new GamesPageAdapter(new ArrayList<>());
        mGamesPageRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mGamesPageRecyclerView.setAdapter(mGamesAdapter);

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(getContext(), getActivity().getSupportLoaderManager());
        mGamesPagePresenter = new GamesPagePresenter(lifecycleHandler, this);

        mGamesPagePresenter.getGames(type);

        return layout;
    }

    @Override
    public void getGames() {

    }

    @Override
    public void updateAdapter(List<GamePreview> gamePreviews) {
        mGamesAdapter.changeDataSet(gamePreviews);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mGamesPageRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
        mGamesPageRecyclerView.setVisibility(View.VISIBLE);
    }
}
