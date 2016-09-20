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

import com.recentgames.GamesType;
import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Image;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.OnSearchStateChanged;
import com.recentgames.router.impl.GamesRouterImpl;

public class GamesFragment extends Fragment implements OnSearchStateChanged {

    public static GamesFragment newInstance() {
        return new GamesFragment();
    }

    private GamesRouter mGamesRouter;

    private View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_games, container, false);
        mGamesRouter = new GamesRouterImpl(getActivity().getSupportFragmentManager());
        setupGameFragments(layout);
        setHasOptionsMenu(true);
        initToolbar((Toolbar) layout.findViewById(R.id.games_toolbar), R.string.app_name);

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
                //mPresenter.onMenuSearchClick();
                mGamesRouter.navigateToSearch();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onSearchRemoved() {
        AppBarLayout appBarLayout = (AppBarLayout) layout.findViewById(R.id.games_appbar);
        appBarLayout.setExpanded(true, true);
    }

    @Override
    public void onSearchAdded() {
        AppBarLayout appBarLayout = (AppBarLayout) layout.findViewById(R.id.games_appbar);
        appBarLayout.setExpanded(false, true);
    }

    public void initToolbar(Toolbar toolbar, int titleResId) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(titleResId);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        Image image = new Image("http://www.giantbomb.com/api/image/screen_medium/2669576-destiny%20v2.jpg");
        GamePreview preview = new GamePreview(36067, image, "Destiny");
        toolbar.setOnClickListener(v -> mGamesRouter.navigateFromGamesToGameDetails(preview));
    }

    private void setupGameFragments(View layout) {
        ViewPager gameFragmentsPages = (ViewPager) layout.findViewById(R.id.view_pager);
        TabLayout gameFragmentsTabs = (TabLayout) layout.findViewById(R.id.tab_layout);

        GameFragmentsAdapter gameFragmentsAdapter = new GameFragmentsAdapter(getActivity().getSupportFragmentManager());
        gameFragmentsAdapter.addFragment(GameItemFragment.newInstance(GamesType.WEEK));
        gameFragmentsAdapter.addFragment(GameItemFragment.newInstance(GamesType.MONTH));
        gameFragmentsAdapter.addFragment(GameItemFragment.newInstance(GamesType.YEAR));

        gameFragmentsPages.setAdapter(gameFragmentsAdapter);
        gameFragmentsPages.offsetLeftAndRight(2);
        gameFragmentsTabs.setupWithViewPager(gameFragmentsPages);
    }

}
