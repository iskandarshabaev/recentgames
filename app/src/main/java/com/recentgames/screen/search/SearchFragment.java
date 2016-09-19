package com.recentgames.screen.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.router.GamesRouter;
import com.recentgames.router.impl.GamesRouterImpl;
import com.recentgames.screen.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class SearchFragment extends Fragment
        implements SearchView, SearchAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.search_toolbar)
    Toolbar mToolbar;

    private SearchPresenter mPresenter;
    private SearchAdapter mAdapter;

    protected GamesRouter mGamesRouter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        mGamesRouter= new GamesRouterImpl(getActivity().getSupportFragmentManager());

        ButterKnife.bind(this, layout);

        initToolbar();

        mAdapter = new SearchAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(getActivity(), getActivity().getSupportLoaderManager());
        mPresenter = new SearchPresenter(this,lifecycleHandler);

        setHasOptionsMenu(true);

        return layout;
    }

    public void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        if(activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showGames(List<GamePreview> games) {
        mAdapter.changeDataSet(games);
    }

    @Override
    public void showError() {

    }

    @Override
    public void onItemClick(@NonNull View view, @NonNull GamePreview game) {
        mGamesRouter.navigateFromSearchToGameDetails(game);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);

        final MenuItem menuitem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView search = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menuitem);

        menuitem.expandActionView();

        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.length() == 0)
                    mPresenter.clear();
                else if(newText.length() >= 3)
                    mPresenter.searchGame(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}
