package com.recentgames.screen.games;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.GamesType;
import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.impl.GamesRouterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class GamesPageFragment extends Fragment implements GamesPageView {

    private Unbinder mUnbinder;

    private boolean wtf = false;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGamesPagePresenter.getGames(type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_games_page, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        type = getArguments().getInt(KEY_TYPE, TYPE_DEFAULT);
        mGamesRouter = new GamesRouterImpl(getActivity().getSupportFragmentManager());
        //initRecycler(layout);

        mGamesAdapter = new GamesPageAdapter(new ArrayList<>());
        mGamesPageRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mGamesPageRecyclerView.setAdapter(mGamesAdapter);

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(getContext(), getActivity().getSupportLoaderManager());
        mGamesPagePresenter = new GamesPagePresenter(lifecycleHandler, this);

        //mGamesPagePresenter.getGames(type);
        /*if (wtf) {
            String wait = "wait";
        } else {
            mGamesPagePresenter.getGames(type);
            String wait = "wait";
        }*/
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void getGames() {

    }

    @Override
    public void updateAdapter(List<GamePreview> gamePreviews) {
        mGamesAdapter.changeDataSet(gamePreviews);
        wtf = true;
    }

    @Override
    public void showError() {
        String wait = "wait";
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
