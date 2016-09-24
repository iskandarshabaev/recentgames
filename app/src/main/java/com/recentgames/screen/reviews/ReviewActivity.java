package com.recentgames.screen.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.recentgames.R;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.repository.RepositoryProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class ReviewActivity extends AppCompatActivity implements ReviewView {

    public static final String REVIEW_KEY = "review";

    public static void showActivity(@NonNull AppCompatActivity activity,
                                    @NonNull ReviewPreview reviewPreview) {
        Intent intent = new Intent(activity, ReviewActivity.class);
        intent.putExtra(REVIEW_KEY, reviewPreview);
        activity.startActivity(intent);
    }

    @BindView(R.id.content)
    TextView mContentView;

    @BindView(R.id.deck)
    TextView mDeckTextView;

    @BindView(R.id.author_name)
    TextView mAuthorName;

    @BindView(R.id.publishDate)
    TextView mPublishDateTextView;

    @BindView(R.id.rating)
    AppCompatRatingBar mRatingBar;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ReviewPresenter mPresenter;
    private ReviewPreview mReviewPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        LifecycleHandler handler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new ReviewPresenter(this, RepositoryProvider.provideGiantBombRepository(), handler);
        mReviewPreview = (ReviewPreview) getIntent().getSerializableExtra(REVIEW_KEY);
        if (mReviewPreview != null) {
            initToolbar(mToolbar, mReviewPreview.getName() == null ? "" : mReviewPreview.getName());
            mPresenter.loadContent(mReviewPreview.getId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initToolbar(@NonNull Toolbar toolbar, @NonNull String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mAuthorName.setVisibility(View.GONE);
        mPublishDateTextView.setVisibility(View.GONE);
        mDeckTextView.setVisibility(View.GONE);
        mRatingBar.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(REVIEW_KEY, mReviewPreview);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(String description) {
        mContentView.setVisibility(View.VISIBLE);
        mContentView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        int width = mContentView.getWidth();
                        Spanned spanned = Html.fromHtml(description,
                                new URLImageParser(mContentView, getApplicationContext(),
                                        width, 500), null);
                        mContentView.setText(spanned);
                        mContentView.getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
    }

    @Override
    public void showDeck(String deck) {
        mDeckTextView.setVisibility(View.VISIBLE);
        mDeckTextView.setText(deck);
    }

    @Override
    public void showAuthorName(String authorName) {
        mAuthorName.setVisibility(View.VISIBLE);
        mAuthorName.setText(authorName);
    }

    @Override
    public void showScore(int rating) {
        mRatingBar.setVisibility(View.VISIBLE);
        mRatingBar.setRating(rating);
    }

    @Override
    public void showError() {
        Snackbar.make(mContentView, "Loading error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reload", v -> {
                    if(mReviewPreview != null) {
                        mPresenter.clear();
                        mPresenter.loadContent(mReviewPreview.getId());
                    }
                }).show();

    }

    @Override
    public void showPublishDate(String publishDate) {
        mPublishDateTextView.setVisibility(View.VISIBLE);
        mPublishDateTextView.setText(publishDate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
