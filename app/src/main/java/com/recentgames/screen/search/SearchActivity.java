package com.recentgames.screen.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.screen.details.GameDetailsActivity;
import com.recentgames.screen.search.SearchAnimator.Action;
import com.recentgames.util.RxSearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class SearchActivity extends AppCompatActivity implements SearchView, SearchAdapter.OnItemClickListener {

    public static void showActivity(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @BindView(R.id.search_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mSearchRecyclerView;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    private SearchPresenter mPresenter;
    private SearchAdapter mAdapter;
    private String mSearchText;
    private Unbinder mUnbinder;

    private final static String SEARCH = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mUnbinder = ButterKnife.bind(this);
        initView();

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new SearchPresenter(this, lifecycleHandler);

        if(savedInstanceState != null) {
            mSearchText = savedInstanceState.getString(SEARCH,"");
            if(mSearchText.length() > 0) {
                mPresenter.init(mSearchText);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        mUnbinder.unbind();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mSearchText != null && mSearchText.length() != 0)
            outState.putString(SEARCH,mSearchText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem menuitem = menu.findItem(R.id.action_search);
        final RxSearchView rxSearchView = (RxSearchView) MenuItemCompat.getActionView(menuitem);

        if (!TextUtils.isEmpty(mSearchText)) {
            menuitem.expandActionView();
            rxSearchView.setQuery(mSearchText, true);
            rxSearchView.clearFocus();
        }

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
            mToolbar.setVisibility(View.INVISIBLE);
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
        mAdapter.changeDataSet(games);
        SearchAnimator.fade(mSearchRecyclerView, Action.FADEIN).start();
    }

    @Override
    public void showError() {
        Snackbar.make(mSearchRecyclerView, "Loading error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reload", v -> mPresenter.onTextChanged(mSearchText))
                .show();
    }

    @Override
    public void clearSearchResult() {
        SearchAnimator.fade(mSearchRecyclerView, Action.FADEOUT).start();
    }

    @Override
    public void notifyIsNotFound() {
        Toast.makeText(this, getString(R.string.game_not_found, mSearchText), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mSearchRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
        mSearchRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(@NonNull View view, @NonNull GamePreview game) {
        GameDetailsActivity.showActivity(this, game);
    }

    @Override
    public void onItemTouch(@NonNull View v, @NonNull MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case MotionEvent.ACTION_MOVE: // движение
                break;
            case MotionEvent.ACTION_UP: // отпускание
                v.performClick();
            case MotionEvent.ACTION_CANCEL:
                break;
        }
    }
}
