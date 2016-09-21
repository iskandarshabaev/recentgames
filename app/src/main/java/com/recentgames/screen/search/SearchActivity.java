package com.recentgames.screen.search;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.screen.details.GameDetailsActivity;
import com.recentgames.util.RxSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import rx.Observable;

public class SearchActivity extends AppCompatActivity implements SearchView, SearchAdapter.OnItemClickListener {

    public static void showActivity(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @BindView(R.id.search_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mSearchRecyclerView;

    private SearchPresenter mPresenter;
    private SearchAdapter mAdapter;
    private String mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new SearchPresenter(this,lifecycleHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem menuitem = menu.findItem(R.id.action_search);
        final RxSearchView rxSearchView = (RxSearchView) MenuItemCompat.getActionView(menuitem);

        menuitem.expandActionView();
        MenuItemCompat.setOnActionExpandListener(menuitem, new OnActionExpandListenerImpl());

        rxSearchView.setOnRxQueryTextListener(newText -> {
            mSearchText = newText;
            mPresenter.onTextChanged(newText);
        });

        return true;
    }

    private class OnActionExpandListenerImpl implements MenuItemCompat.OnActionExpandListener {

        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            return false;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            onBackPressed();
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mAdapter = new SearchAdapter(this);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showGames(List<GamePreview> games) {
        mPresenter.notifyIsNotFound(games);

        if(games.size() == 0) {
            ObjectAnimator mover = ObjectAnimator.ofFloat(mSearchRecyclerView,"translationY", 0f, -mSearchRecyclerView.getHeight());
            mover.setDuration(700);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(mSearchRecyclerView, "alpha", 1f, 0f);
            fadeIn.setDuration(700);
            AnimatorSet animatorSet = new AnimatorSet();

            animatorSet.play(mover).with(fadeIn);
            animatorSet.start();
            mSearchRecyclerView.postDelayed(() -> mAdapter.changeDataSet(games),700);
        }


        if(games.size() != 0) {
            boolean b = mAdapter.getItemCount() > 0;
            mAdapter.changeDataSet(games);
            ObjectAnimator mover = ObjectAnimator.ofFloat(mSearchRecyclerView, "translationY", -mSearchRecyclerView.getHeight(), 0f);
            mover.setDuration(700);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(mSearchRecyclerView, "alpha", 0f, 1f);
            fadeIn.setDuration(700);
            ObjectAnimator mover1 = ObjectAnimator.ofFloat(mSearchRecyclerView, "translationY", 0f, -mSearchRecyclerView.getHeight());
            mover1.setDuration(700);
            ObjectAnimator fadeIn1 = ObjectAnimator.ofFloat(mSearchRecyclerView, "alpha", 1f, 0f);
            fadeIn1.setDuration(700);
            AnimatorSet animatorSet = new AnimatorSet();

            if(!b)
                animatorSet.play(mover).with(fadeIn);
            else
                animatorSet.play(mover1).with(fadeIn1).before(mover).with(fadeIn);

            animatorSet.setInterpolator(new OvershootInterpolator(2));
            animatorSet.start();
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void clearSearchResult() {
        mAdapter.changeDataSet(new ArrayList<>());
    }

    @Override
    public void notifyIsNotFound() {
        Toast.makeText(this,getString(R.string.game_not_found,mSearchText),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onItemClick(@NonNull View view, @NonNull GamePreview game) {
        GameDetailsActivity.showActivity(this,game);
    }
}
