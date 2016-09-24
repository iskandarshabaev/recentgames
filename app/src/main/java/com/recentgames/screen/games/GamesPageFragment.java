package com.recentgames.screen.games;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.rxloader.GamePreviewLifecycleHandler;
import com.recentgames.rxloader.GamePreviewLoaderLifecycleHandler;
import com.recentgames.screen.details.GameDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GamesPageFragment extends Fragment implements
        GamesPageView,
        GamesPageAdapter.OnItemClickListener,
        SwipyRefreshLayout.OnRefreshListener {

    @BindView(R.id.games_page_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.refresh)
    SwipyRefreshLayout mSwipyRefreshLayout;
    @BindView(R.id.games_page_recycler)
    RecyclerView mGamesPageRecyclerView;

    private GamesPagePresenter mGamesPagePresenter;
    private GamesPageAdapter mGamesAdapter;

    private static final String KEY_TYPE = "KEY_TYPE";
    private static final int TYPE_DEFAULT = -1;
    private int mType;
    private int mPortraitSpanCount = 2;
    private int mLandscapeSpanCount = 3;

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
        mType = getArguments().getInt(KEY_TYPE, TYPE_DEFAULT);

        initRecycler();
        initPresenter();
        return layout;
    }

    private void initRecycler() {
        mGamesAdapter = new GamesPageAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? mPortraitSpanCount : mLandscapeSpanCount);
        mGamesPageRecyclerView.setLayoutManager(layoutManager);
        mGamesPageRecyclerView.setAdapter(mGamesAdapter);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setDistanceToTriggerSync(240);
    }

    private void initPresenter() {
        GamePreviewLifecycleHandler lifecycleHandler = GamePreviewLoaderLifecycleHandler.create(getContext(), getActivity().getSupportLoaderManager());
        mGamesPagePresenter = new GamesPagePresenter(this, lifecycleHandler, RepositoryProvider.provideGiantBombRepository());
        mGamesPagePresenter.init(mType);
    }

    @Override
    public void updateAdapter(List<GamePreview> gamePreviews) {
        mGamesAdapter.addGames(gamePreviews);
    }

    @Override
    public void changeGames(List<GamePreview> gamePreviews) {
        mGamesAdapter.refreshGames(gamePreviews);
    }

    @Override
    public void showError() {
        Snackbar.make(mGamesPageRecyclerView, R.string.loading_error, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showErrorWithRetry() {
        Snackbar.make(mGamesPageRecyclerView, R.string.loading_error, Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.reload).toUpperCase(), v -> mGamesPagePresenter.onSnackbarClick(mType))
                .show();
    }

    @Override
    public void showRefreshing() {
        mSwipyRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        mSwipyRefreshLayout.setRefreshing(false);
    }

    @Override
    public void deactivateBottomRefresh() {
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
    }

    @Override
    public void activateBottomRefresh() {
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
    }

    @Override
    public void openGameDetailsScreen(GamePreview gamePreview) {
        GameDetailsActivity.showActivity(getContext(), gamePreview);
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

    @Override
    public void onItemClick(@NonNull View view, @NonNull GamePreview gamePreview) {
        mGamesPagePresenter.onItemClick(gamePreview);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            mGamesPagePresenter.refreshGames(mType);
        } else {
            mGamesPagePresenter.getGames(mType, mGamesAdapter.getItemCount());
        }
    }

}
