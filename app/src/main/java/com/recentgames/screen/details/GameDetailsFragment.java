package com.recentgames.screen.details;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Genre;
import com.recentgames.model.content.Image;
import com.recentgames.model.content.Platform;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.impl.GamesRouterImpl;
import com.recentgames.util.ImageHelper;
import com.recentgames.util.PlatformUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import rx.Observable;

public class GameDetailsFragment extends Fragment implements GameDetailsView {

    public static final String GAME_PREVIEW_KEY = "game_preview";

    private Unbinder mUnbinder;
    private GamesRouter mGamesRouter;
    private GameDetailsPresenter mPresenter;

    @BindView(R.id.poster)
    ImageView mPosterImageView;

    @BindView(R.id.deck)
    TextView mDeckTextView;

    @BindView(R.id.platforms_container)
    LinearLayout mPlatformsContainer;

    @BindView(R.id.genres_container)
    TextView mGenresContainer;

    @BindView(R.id.release_date)
    TextView mReleaseDate;

    @BindView(R.id.rating)
    TextView mRating;

    @BindView(R.id.progressFrame)
    FrameLayout mProgressFrame;

    @BindView(R.id.game_name)
    TextView mGameNameTextView;

    @BindView(R.id.review_list)
    RecyclerView mReviewsRecyclerView;
    private ReviewsAdapter mAdapter;

    @BindView(R.id.images)
    ViewPager mImagesViewPager;
    private ImagesViewPagerAdapter mImagesAdapter;

    public static GameDetailsFragment newInstance(GamePreview game) {
        Bundle args = new Bundle();
        args.putSerializable(GAME_PREVIEW_KEY, game);
        GameDetailsFragment fragment = new GameDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_game_details, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        initToolbar(toolbar);
        initRecyclerView();
        initImagesViewPager();
        mGamesRouter = new GamesRouterImpl(getActivity().getSupportFragmentManager());
        GamePreview game = (GamePreview) getArguments().getSerializable(GAME_PREVIEW_KEY);
        if (game != null) {
            initPresenter(game);
            initPoster(game.getImage());
            toolbar.setTitle("");
            mGameNameTextView.setText(game.getName());
        }
        return layout;
    }

    private void initToolbar(@NonNull Toolbar toolbar) {
        //AppCompatActivity activity = (AppCompatActivity) getActivity();
        //activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            mGamesRouter.navigateBack();
            mPresenter.navigateBack();
        });
    }

    private void initRecyclerView() {
        mAdapter = new ReviewsAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
        mReviewsRecyclerView.setLayoutManager(layoutManager);
        mReviewsRecyclerView.setItemAnimator(animator);
        mReviewsRecyclerView.setAdapter(mAdapter);
    }

    private void initImagesViewPager() {

        ImagesViewPagerAdapter.OnImageClickListener listener = image -> {

        };
        mImagesAdapter = new ImagesViewPagerAdapter(getContext(), listener, new ArrayList<>());
        mImagesViewPager.setAdapter(mImagesAdapter);
        mImagesViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
            getActivity().getWindow().setStatusBarColor(color);
        }
        mUnbinder.unbind();
    }

    private void initPoster(@NonNull Image image) {
        ImageHelper.loadImage(mPosterImageView, image.getMediumUrl());
    }

    private void initPresenter(@NonNull GamePreview game) {
        LifecycleHandler handler = LoaderLifecycleHandler.create(getContext(),
                getActivity().getSupportLoaderManager());
        mPresenter = new GameDetailsPresenter(game, this, handler,
                RepositoryProvider.provideGiantBombRepository());
        mPresenter.init();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showRating(@NonNull String ratings) {
        mRating.setText(ratings);
    }

    @Override
    public void showImages(@NonNull List<Image> images) {
        mImagesAdapter.changeDataSet(images);
    }

    @Override
    public void showReleaseDate(@NonNull String dateString) {
        mReleaseDate.setText(dateString);
    }

    @Override
    public void showPlatforms(@NonNull List<Platform> platforms) {
        Observable.from(platforms)
                .map(Platform::getId)
                .subscribe(
                        this::addPlatform,
                        Throwable::printStackTrace);
    }

    private void addPlatform(int id) {
        int margin = getResources().getDimensionPixelSize(R.dimen.margin_medium);
        int width = getResources().getDimensionPixelSize(R.dimen.width_24);
        int height = getResources().getDimensionPixelSize(R.dimen.width_24);
        ImageView platformIcon = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(0, 0, margin, 0);
        platformIcon.setImageResource(PlatformUtils.getDrawableResId(id));
        int color = ContextCompat.getColor(getContext(), PlatformUtils.getColorResId(id));
        platformIcon.setColorFilter(color);
        platformIcon.setLayoutParams(params);
        mPlatformsContainer.addView(platformIcon);
    }

    @Override
    public void showReviews(@NonNull List<ReviewPreview> previews) {
        mAdapter.changeDataSet(previews);
    }

    @Override
    public void showDeck(@NonNull String text) {
        if (Build.VERSION.SDK_INT >= 24) {
            mDeckTextView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mDeckTextView.setText(Html.fromHtml(text));
        }
    }

    @Override
    public void showGenres(@NonNull List<Genre> genres) {
        Observable.from(genres)
                .map(Genre::getName)
                .reduce((s, s2) -> s + ", " + s2)
                .subscribe(text -> mGenresContainer.setText(text), Throwable::printStackTrace);
    }

    @Override
    public void showLoading() {
        mProgressFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressFrame.setVisibility(View.GONE);
    }
}
