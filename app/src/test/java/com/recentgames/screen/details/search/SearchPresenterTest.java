package com.recentgames.screen.details.search;

import android.support.annotation.NonNull;

import com.recentgames.model.content.GamePreview;
import com.recentgames.repository.DefaultGiantBombRepository;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.screen.search.SearchPresenter;
import com.recentgames.screen.search.SearchView;
import com.recentgames.test.MockLifecycleHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

/**
 * Created by rodionov on 21.09.2016.
 */

@RunWith(JUnit4.class)
public class SearchPresenterTest {

    private SearchView mSearchView;
    private SearchPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        LifecycleHandler lifecycleHandler = new MockLifecycleHandler();
        
        mSearchView = Mockito.mock(SearchView.class);
        mPresenter = new SearchPresenter(mSearchView,lifecycleHandler);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testNoActionsWithView() throws Exception {
        Mockito.verifyNoMoreInteractions(mSearchView);
    }

    @Test
    public void testTextChanged() throws Exception {

        GamePreview game = mock(GamePreview.class);
        ListGamePreview.setGame(Arrays.asList(game));
        RepositoryProvider.setGiantBombRepository(new SearchTestRepository());

        mPresenter.onTextChanged("gta");
        Mockito.verify(mSearchView).showLoading();
        Mockito.verify(mSearchView).hideLoading();
        Mockito.verify(mSearchView).showGames(ListGamePreview.getGame());

        mPresenter.onTextChanged("");
        Mockito.verify(mSearchView).clearSearchResult();

        mPresenter.onTextChanged("unknow");
        Mockito.verify(mSearchView, times(2)).showLoading();
        Mockito.verify(mSearchView, times(2)).hideLoading();
        Mockito.verify(mSearchView).notifyIsNotFound();
        Mockito.verify(mSearchView).showGames(ListGamePreview.getGame());
        Mockito.verify(mSearchView).showGames(new ArrayList<>());
    }

    @Test
    public void testShowError() throws Exception {
        RepositoryProvider.setGiantBombRepository(new SearchTestRepository());
        mPresenter.onTextChanged("123");
        Mockito.verify(mSearchView, times(1)).showError();
    }

    @Test
    public void testUnsubscribe() throws Exception {
        mPresenter.unsubscribe();
    }

    private static class SearchTestRepository extends DefaultGiantBombRepository {

        @NonNull
        @Override
        public Observable<List<GamePreview>> search(String name) {
            if("gta".equals(name)) {

                return Observable.just(ListGamePreview.getGame());
            } else if("unknow".equals(name)) {
                return Observable.just(new ArrayList<>());
            }
            return Observable.error(new IOException());
        }
    }

    private static class ListGamePreview {

        private static List<GamePreview> sList;

        public static List<GamePreview> getGame(){
            if (sList == null) {
                GamePreview game = mock(GamePreview.class);
                sList = Arrays.asList(game);
            }
            return sList;
        }

        public static void setGame(List<GamePreview> list) {
            sList = list;
        }

    }
}
