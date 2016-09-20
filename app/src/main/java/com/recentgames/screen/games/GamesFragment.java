package com.recentgames.screen.games;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Image;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.OnSearchStateChanged;
import com.recentgames.router.impl.GamesRouterImpl;

import java.util.Date;

public class GamesFragment extends Fragment implements OnSearchStateChanged, GamesView {

    public static GamesFragment newInstance() {
        return new GamesFragment();
    }

    private GamesRouter mGamesRouter;
    private GamesPresenter mGamesPresenter;
    private View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_games, container, false);
        mGamesRouter = new GamesRouterImpl(getActivity().getSupportFragmentManager());
        setHasOptionsMenu(true);
        initToolbar((Toolbar) layout.findViewById(R.id.games_toolbar), R.string.app_name);
        setupGameFragments(layout);
        mGamesPresenter = new GamesPresenter(this);
        mGamesRouter.addSearchStateListener(this);

        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_games, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

    @Override
    public void onSearchRemoved() {
        mGamesPresenter.onSearchRemoved();
    }

    @Override
    public void onSearchAdded() {
        mGamesPresenter.onSearchAdded();
    }

    public void initToolbar(Toolbar toolbar, int titleResId) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(titleResId);
        toolbar.setTitleTextColor(R.color.white);
        toolbar.setOnClickListener(v -> mGamesRouter.navigateFromGamesToGameDetails(getFakeGamePreview()));
    }

    private GamePreview getFakeGamePreview() {
        int id = 49962;
        Image image = new Image("http://www.giantbomb.com/api/image/screen_medium/2883851-recore%20v1.jpg");
        String gameName = "ReCore";
        return new GamePreview(id, image, gameName, new Date());
    }

    private void setupGameFragments(View layout) {
        ViewPager gameFragmentsPages = (ViewPager) layout.findViewById(R.id.view_pager);
        TabLayout gameFragmentsTabs = (TabLayout) layout.findViewById(R.id.tab_layout);

        GameFragmentsAdapter gameFragmentsAdapter = new GameFragmentsAdapter(getChildFragmentManager());

        gameFragmentsPages.setAdapter(gameFragmentsAdapter);
        gameFragmentsTabs.setupWithViewPager(gameFragmentsPages);
    }

    @Override
    public void collapseAppBar() {
        AppBarLayout appBarLayout = (AppBarLayout) layout.findViewById(R.id.games_appbar);
        appBarLayout.setExpanded(false, true);
    }

    @Override
    public void expandAppBar() {
        AppBarLayout appBarLayout = (AppBarLayout) layout.findViewById(R.id.games_appbar);
        appBarLayout.setExpanded(true, true);
    }

    @Override
    public void openSearchFragment() {
        mGamesRouter.navigateToSearch();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
