package com.recentgames.screen.games;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.recentgames.R;
import com.recentgames.screen.search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GamesActivity extends AppCompatActivity implements GamesView {

    @BindView(R.id.games_toolbar)
    Toolbar mToolbar;

    private GamesPresenter mGamesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        ButterKnife.bind(this);

        initToolbar();
        setupGameFragments();

        mGamesPresenter = new GamesPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_search:
                mGamesPresenter.onMenuSearchClick();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
    }

    private void setupGameFragments() {
        ViewPager gameFragmentsPages = (ViewPager) findViewById(R.id.view_pager);
        TabLayout gameFragmentsTabs = (TabLayout) findViewById(R.id.tab_layout);

        GameFragmentsAdapter gameFragmentsAdapter = new GameFragmentsAdapter(getSupportFragmentManager());

        gameFragmentsPages.setAdapter(gameFragmentsAdapter);
        gameFragmentsTabs.setupWithViewPager(gameFragmentsPages);
    }

    @Override
    public void openSearchScreen() {
        SearchActivity.showActivity(this);
    }
}
