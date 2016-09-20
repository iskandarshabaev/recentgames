package com.recentgames.screen.details;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pixplicity.multiviewpager.MultiViewPager;
import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Genre;
import com.recentgames.model.content.Image;
import com.recentgames.model.content.Platform;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.repository.RepositoryProvider;
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

public class GameDetailsActivity extends AppCompatActivity implements GameDetailsView{

    public static void showActivity(Context context, GamePreview game) {
        //Bundle args = new Bundle();
        //args.putSerializable(GAME_PREVIEW_KEY, game);
        Intent intent = new Intent(context, GameDetailsActivity.class);
        intent.putExtra(GAME_PREVIEW_KEY, game);
        context.startActivity(intent);
    }

    public static final String GAME_PREVIEW_KEY = "game_preview";
    private Unbinder mUnbinder;
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
    @BindView(R.id.progress)
    ProgressBar mProgressFrame;
    @BindView(R.id.game_name)
    TextView mGameNameTextView;
    @BindView(R.id.review_list)
    RecyclerView mReviewsRecyclerView;
    private ReviewsAdapter mReviewsAdapter;
    @BindView(R.id.images)
    MultiViewPager mImagesViewPager;
    private ImagesViewPagerAdapter mImagesAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.images_card)
    CardView mImagesCard;
    @BindView(R.id.similar_games)
    RecyclerView mSimilarGamesRecyclerView;
    SimilarGamesAdapter mSimilarGamesAdapter;
    @BindView(R.id.similar_games_card)
    CardView mSimilarGamesCard;
    @BindView(R.id.reviews_card)
    CardView mReviewsCard;
    @BindView(R.id.description_card)
    CardView mDescriptionCard;
    @BindView(R.id.contentScrollPanel)
    NestedScrollView mContentNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game_details);
        mUnbinder = ButterKnife.bind(this);
        paintStatusBar(R.color.game_details_status_color);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(mToolbar);

        mReviewsAdapter = new ReviewsAdapter(new ArrayList<>(), reviewPreview -> {

        });
        initRecyclerView(mReviewsRecyclerView, mReviewsAdapter);

        mSimilarGamesAdapter = new SimilarGamesAdapter(new ArrayList<>(), game -> {
            mPresenter.clear();
            GameDetailsActivity.showActivity(this, game);
            finish();
        });
        initRecyclerView(mSimilarGamesRecyclerView, mSimilarGamesAdapter);

        initImagesViewPager();

        GamePreview game = (GamePreview) getIntent().getSerializableExtra(GAME_PREVIEW_KEY);
        if (game != null) {
            initPresenter(game);
            if(game.getImage() != null) {
                showPoster(game.getImage());
            }
            mGameNameTextView.setText(game.getName());
        }
    }

    private void initToolbar(@NonNull Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void paintStatusBar(@ColorRes int colorResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = ContextCompat.getColor(this, colorResId);
            getWindow().setStatusBarColor(color);
        }
    }

    private void initRecyclerView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(animator);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void initImagesViewPager() {
        ImagesViewPagerAdapter.OnImageClickListener listener = image -> {

        };
        mImagesAdapter = new ImagesViewPagerAdapter(new ArrayList<>(), listener);
        mImagesViewPager.setAdapter(mImagesAdapter);
        //mImagesViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        mUnbinder.unbind();
        paintStatusBar(R.color.colorPrimaryDark);
    }

    @Override
    public void showPoster(Image image) {
        if(image != null && image.getMediumUrl() != null) {
            ImageHelper.loadImage(mPosterImageView, image.getMediumUrl());
        }
    }

    private void initPresenter(@NonNull GamePreview game) {
        LifecycleHandler handler = LoaderLifecycleHandler.create(this,
                getSupportLoaderManager());
        mPresenter = new GameDetailsPresenter(game, this, handler,
                RepositoryProvider.provideGiantBombRepository());
        mPresenter.init();
    }

    @Override
    public void showError() {
        Snackbar.make(mContentNestedScrollView, "Loading error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reload", v -> {
                    mPresenter.clear();
                    mPresenter.init();
                }).show();
    }

    @Override
    public void showRating(@NonNull String ratings) {
        mRating.setText(ratings);
    }

    @Override
    public void showImages(@NonNull List<Image> images) {
        mImagesCard.setVisibility(View.VISIBLE);
        mImagesAdapter.changeDataSet(images);
        scrollToHead(mImagesViewPager);
    }

    @Override
    public void showReleaseDate(@NonNull String dateString) {
        mReleaseDate.setText(dateString);
    }

    @Override
    public void showPlatforms(@NonNull List<Platform> platforms) {
        Observable.from(platforms)
                .map(Platform::getId)
                .filter(PlatformUtils.platforms::contains)
                .subscribe(
                        this::addPlatform,
                        Throwable::printStackTrace);
    }

    private void addPlatform(int id) {
        int margin = getResources().getDimensionPixelSize(R.dimen.margin_medium);
        int width = getResources().getDimensionPixelSize(R.dimen.width_24);
        int height = getResources().getDimensionPixelSize(R.dimen.width_24);
        ImageView platformIcon = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(0, 0, margin, 0);
        platformIcon.setImageResource(PlatformUtils.getDrawableResId(id));
        int color = ContextCompat.getColor(this, PlatformUtils.getColorResId(id));
        platformIcon.setColorFilter(color);
        platformIcon.setLayoutParams(params);
        mPlatformsContainer.addView(platformIcon);
    }

    @Override
    public void showReviews(@NonNull List<ReviewPreview> previews) {
        mReviewsCard.setVisibility(View.VISIBLE);
        mReviewsAdapter.changeDataSet(previews);
        scrollToHead(mContentNestedScrollView);
    }

    @Override
    public void showDeck(@NonNull String text) {
        mDescriptionCard.setVisibility(View.VISIBLE);
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
    public void showSimilarGames(@NonNull List<GamePreview> similarGames) {
        mSimilarGamesCard.setVisibility(View.VISIBLE);
        mSimilarGamesAdapter.changeDataSet(similarGames);
        scrollToHead(mContentNestedScrollView);
    }

    private void scrollToHead(View view){
        view.post(() -> view.scrollTo(0, 0));
    }

    @Override
    public void showLoading() {
        mProgressFrame.setVisibility(View.VISIBLE);
        mDescriptionCard.setVisibility(View.GONE);
        mImagesCard.setVisibility(View.GONE);
        mSimilarGamesCard.setVisibility(View.GONE);
        mReviewsCard.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mProgressFrame.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                mPresenter.clear();
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPresenter.clear();
    }
}
