package com.recentgames.screen.games;

import com.recentgames.GamesType;
import com.recentgames.exception.EmptyCacheException;
import com.recentgames.exception.LimitReachedException;
import com.recentgames.exception.RefreshException;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.GamePreviewCached;
import com.recentgames.model.content.Image;
import com.recentgames.repository.GiantBombRepository;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.rxloader.GamePreviewLifecycleHandler;
import com.recentgames.test.MockGamePreviewLifecycleHandler;
import com.recentgames.utils.RxSchedulersHooks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import java.util.List;

import rx.Observable;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class GamesPagePresenterTest {

    @Mock
    private GamesPageView mView;
    @Mock
    private GiantBombRepository mRepository;
    private GamesPagePresenter mPresenter;
    //@Mock
    //private GamesPagePresenter mSpyPresenter;
    private GamePreview mGame;
    private GamePreviewLifecycleHandler mLifecycleHandler;

    @Before
    public void setUp() throws Exception {
        RxSchedulersHooks.immediate();
        Image image = new Image("http://www.giantbomb.com/api/image/scale_medium/2669576-destiny%20v2.jpg");
        mGame = new GamePreview(36067, image, "Destiny", null);
        mView = mock(GamesPageView.class);
        mLifecycleHandler = new MockGamePreviewLifecycleHandler();
        mRepository = mock(GiantBombRepository.class);
        RepositoryProvider.setGiantBombRepository(mRepository);
        //mRepository = RepositoryProvider.provideGiantBombRepository();
        mPresenter = new GamesPagePresenter(mView, mLifecycleHandler, mRepository);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void testOnGameCLick() throws Exception {
        mPresenter.onItemClick(mGame);
        verify(mView).openGameDetailsScreen(mGame);
    }

    @Test
    public void testOnSnackbarClick() throws Exception {
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), false);
        int type = GamesType.WEEK;
        int offset = 0;
        when(mRepository.games(type, offset)).thenReturn(Observable.just(gamePreviewCached));

        mPresenter.onSnackbarClick(type);
        verify(mView, times(1)).showRefreshing();
        verify(mView, times(1)).hideRefreshing();
    }

    @Test
    public void testLoadGames() throws Exception { //first 20 games (or less)
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), false);
        int type = GamesType.WEEK;
        int offset = 0;
        when(mRepository.games(type, offset)).thenReturn(Observable.just(gamePreviewCached));
        mPresenter.getGames(type, offset);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).updateAdapter(gamePreviewCached.getGamePreviews());
        verify(mView, times(0)).showRefreshing();
        verify(mView, times(0)).hideRefreshing();
        verify(mView, times(0)).showError();
        verify(mView, times(0)).deactivateBottomRefresh();
    }

    @Test
    public void testAddGames() throws Exception { //add games after scroll to bottom
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), false);
        int type = GamesType.YEAR;
        int offset = 40;
        when(mRepository.games(type, offset)).thenReturn(Observable.just(gamePreviewCached));
        mPresenter.getGames(type, offset);
        verify(mView).showRefreshing();
        verify(mView).hideRefreshing();
        verify(mView).updateAdapter(gamePreviewCached.getGamePreviews());
        verify(mView, times(0)).showLoading();
        verify(mView, times(0)).hideLoading();
        verify(mView, times(0)).showError();
        verify(mView, times(0)).deactivateBottomRefresh();
    }

    @Test
    public void testAddGamesFailed() throws Exception { //first 20 games (or less)
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), true);
        int type = GamesType.YEAR;
        int offset = 40;
        //when(mRepository.games(type, offset)).thenThrow(new LimitReachedException("Message"));
        when(mRepository.games(type, offset)).thenReturn(Observable.error(new RefreshException("Useless cache")));
        mPresenter.getGames(type, offset);
        verify(mView, times(1)).showRefreshing();
        verify(mView, times(1)).hideRefreshing();
        verify(mView, times(1)).showError();
    }

    @Test
    public void testGetGamesFailed() throws Exception { //first 20 games (or less)
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), true);
        int type = GamesType.WEEK;
        int offset = 0; //imagine that we have only 25 games
        when(mRepository.games(type, offset)).thenReturn(Observable.error(new EmptyCacheException("Cache is empty")));
        mPresenter.getGames(type, offset);
        verify(mView, times(1)).showLoading();
        verify(mView, times(1)).hideLoading();
        verify(mView, times(1)).showError();

        verify(mView, times(0)).updateAdapter(gamePreviewCached.getGamePreviews());
        verify(mView, times(0)).showRefreshing();
        verify(mView, times(0)).hideRefreshing();
    }

    @Test
    public void testLimitReached() throws Exception { //means that we have no more games
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), true);
        int type = GamesType.WEEK;
        int offset = 250; //imagine that we have only 25 games
        when(mRepository.games(type, offset)).thenReturn(Observable.error(new LimitReachedException("No more games for you guys")));
        mPresenter.getGames(type, offset);
        verify(mView, times(1)).showRefreshing();
        verify(mView, times(1)).hideRefreshing();
        verify(mView, times(1)).deactivateBottomRefresh();

        verify(mView, times(0)).updateAdapter(gamePreviewCached.getGamePreviews());
        verify(mView, times(0)).showLoading();
        verify(mView, times(0)).hideLoading();
        verify(mView, times(0)).showError();

    }

    @Test
    public void testRefreshGames() throws Exception {
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), false);
        int type = GamesType.WEEK;
        int offset = 0;
        when(mRepository.games(type, offset)).thenReturn(Observable.just(gamePreviewCached));
        mPresenter.refreshGames(type);
        verify(mView).hideRefreshing();
        verify(mView).activateBottomRefresh(); //for example bottom refresh was lock so we should activate
        verify(mView).changeGames(gamePreviewCached.getGamePreviews());
        verify(mView, times(0)).showRefreshing(); //will show refreshing by himself
        verify(mView, times(0)).showLoading();
        verify(mView, times(0)).hideLoading();
        verify(mView, times(0)).showError();
        verify(mView, times(0)).deactivateBottomRefresh();
    }

    @Test
    public void testRefreshGamesCache() throws Exception {
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), true); //let's check cached data
        int type = GamesType.WEEK;
        int offset = 0;
        when(mRepository.games(type, offset)).thenReturn(Observable.just(gamePreviewCached));
        mPresenter.refreshGames(type);
        verify(mView).hideRefreshing();
        verify(mView).deactivateBottomRefresh(); //no more additional games for you
        verify(mView).changeGames(gamePreviewCached.getGamePreviews());
        verify(mView, times(0)).showRefreshing(); //will show refreshing by himself
        verify(mView, times(0)).showLoading();
        verify(mView, times(0)).hideLoading();
        verify(mView, times(0)).showError();
        verify(mView, times(0)).activateBottomRefresh();
    }

    @Test
    public void testRefreshGamesFailed() throws Exception { //exception and no cache data
        int type = GamesType.WEEK;
        int offset = 0;
        when(mRepository.games(type, offset)).thenReturn(Observable.error(new EmptyCacheException("Cache is empty")));
        mPresenter.refreshGames(type);
        verify(mView).hideRefreshing();
        verify(mView).showErrorWithRetry();
        verify(mView, times(0)).showRefreshing(); //will show refreshing by himself
        verify(mView, times(0)).showLoading();
        verify(mView, times(0)).hideLoading();
        verify(mView, times(0)).changeGames(anyList());
        verify(mView, times(0)).activateBottomRefresh();
        verify(mView, times(0)).deactivateBottomRefresh();
    }

    @Test
    public void testUsage() throws Exception {
        GamePreviewCached gamePreviewCached = new GamePreviewCached(mock(List.class), false);
        int type = GamesType.WEEK;
        int offset = 0;
        when(mRepository.games(type, offset)).thenReturn(Observable.just(gamePreviewCached));
        mPresenter.refreshGames(type);
        verify(mView).hideRefreshing();
        verify(mView).activateBottomRefresh(); //for example bottom refresh was lock so we should activate
        verify(mView).changeGames(gamePreviewCached.getGamePreviews());

        mPresenter.onItemClick(mGame);
        verify(mView).openGameDetailsScreen(mGame);
    }

}