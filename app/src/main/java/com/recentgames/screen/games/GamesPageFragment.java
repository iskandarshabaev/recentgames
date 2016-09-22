package com.recentgames.screen.games;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.rxloader.GamePreviewLifecycleHandler;
import com.recentgames.rxloader.GamePreviewLoaderLifecycleHandler;
import com.recentgames.screen.details.GameDetailsActivity;
import com.recentgames.util.HidingScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GamesPageFragment extends Fragment implements GamesPageView, GamesPageAdapter.OnItemClickListener {

    @BindView(R.id.games_page_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.games_page_recycler)
    RecyclerView mGamesPageRecyclerView;

    private GamesPagePresenter mGamesPagePresenter;
    private GamesPageAdapter mGamesAdapter;

    private static final String KEY_TYPE = "KEY_TYPE";
    private static final int TYPE_DEFAULT = -1;
    private int mType;
    private int mSpanCount = 2;
    private boolean mIsLoading = false; //todo: move to presenter

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

        mGamesAdapter = new GamesPageAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), mSpanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // FIXME: 9/22/16 true should means footer
                if (mGamesAdapter.isFooter(position)) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        mGamesPageRecyclerView.setLayoutManager(layoutManager);
        mGamesPageRecyclerView.setAdapter(mGamesAdapter);
        mGamesPageRecyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onBottomReached() {
                if (mIsLoading) return;
                mIsLoading = true;
                mGamesPagePresenter.getGames(mType, mGamesAdapter.getItemCount());
            }
        });

        GamePreviewLifecycleHandler lifecycleHandler = GamePreviewLoaderLifecycleHandler.create(getContext(), getActivity().getSupportLoaderManager());
        mGamesPagePresenter = new GamesPagePresenter(lifecycleHandler, this);

        //move to mPresenter.init(type, offset);
        mGamesPagePresenter.getGames(mType, mGamesAdapter.getItemCount());

        return layout;
    }

    @Override
    public void updateAdapter(List<GamePreview> gamePreviews) {
        mGamesAdapter.changeDataSet(gamePreviews);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showRecyclerLoading() {
        mGamesAdapter.addFooter();
    }

    @Override
    public void hideRecyclerLoading() {
        mGamesAdapter.removeFooter();
        mIsLoading = false;
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
}
