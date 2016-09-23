package com.recentgames.screen.details;

import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Image;
import com.recentgames.model.content.Rating;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.repository.GiantBombRepository;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.test.MockLifecycleHandler;
import com.recentgames.utils.RxSchedulersHooks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import java.util.Date;

import io.realm.RealmList;
import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class GameDetailsPresenterTest {

    @Mock
    private GameDetailsView mView;
    @Mock
    private GiantBombRepository mRepository;

    private GameDetailsPresenter mPresenter;
    private GamePreview mGame;
    private LifecycleHandler mLifecycleHandler;

    @Before
    public void setUp() throws Exception {
        RxSchedulersHooks.immediate();
        Image image = new Image("http://www.giantbomb.com/api/image/scale_medium/2669576-destiny%20v2.jpg");
        mGame = new GamePreview(36067, image, "Destiny",new Date());
        mLifecycleHandler = new MockLifecycleHandler();
        mView = mock(GameDetailsView.class);
        mRepository = mock(GiantBombRepository.class);
        RepositoryProvider.setGiantBombRepository(mRepository);
        mPresenter = new GameDetailsPresenter(mGame, mView, mLifecycleHandler,
                RepositoryProvider.provideGiantBombRepository());
    }

    @Test
    public void initTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        gameDescription.setGenres(new RealmList<>());
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView).showPoster(gameDescription.getImage());
        verify(mView).showPlatforms(gameDescription.getPlatforms());
        verify(mView).showGenres(gameDescription.getGenres());
        verify(mView).showDeck(gameDescription.getDeck());
        verify(mView).showGenres(gameDescription.getGenres());
    }

    @Test
    public void initShowDateTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        String releaseDate = "2013-04-02 00:00:00";
        gameDescription.setOriginalReleaseDate(releaseDate);
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView).showReleaseDate(anyString());
    }

    @Test
    public void initShowEmptyDateTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        gameDescription.setOriginalReleaseDate(null);
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView, never()).showReleaseDate(anyString());
    }

    @Test
    public void initShowImagesTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        RealmList<Image> images = new RealmList<>();
        gameDescription.setImages(images);
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView).showImages(images);
    }

    @Test
    public void initShowEmptyImagesTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        gameDescription.setImages(null);
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView, never()).showImages(anyList());
    }

    @Test
    public void initShowRatingsTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        RealmList<Rating> ratings = new RealmList<>();
        ratings.add(new Rating(0, "1"));
        ratings.add(new Rating(2, "2"));
        gameDescription.setOriginalGameRating(ratings);
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView).showRating("1, 2");
    }

    @Test
    public void initShowEmptyRatingsTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        RealmList<Rating> ratings = new RealmList<>();
        gameDescription.setOriginalGameRating(ratings);
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView, never()).showRating(anyString());
    }

    @Test
    public void initShowSimilarGamesTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        RealmList<GamePreview> games = new RealmList<>();
        gameDescription.setSimilarGames(games);
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView).showSimilarGames(games);
    }

    @Test
    public void initShowEmptySimilarGamesTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView, never()).showSimilarGames(anyList());
    }

    @Test
    public void initShowReviewsTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        RealmList<ReviewPreview> reviews = new RealmList<>();
        gameDescription.setReviews(reviews);
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView).showReviews(reviews);
    }

    @Test
    public void initShowEmptyReviewsTest() throws Exception {
        GameDescription gameDescription = new GameDescription();
        when(mRepository.game(mGame.getId())).thenReturn(Observable.just(gameDescription));
        mPresenter.init();
        verify(mView, never()).showReviews(anyList());
    }

    @Test
    public void initWithExceptionTest() throws Exception {
        when(mRepository.game(mGame.getId())).thenReturn(Observable.error(new Exception()));
        mPresenter.init();
        verify(mView).showError();
    }

    @Test
    public void clearTest() {
        mPresenter.clear();
        //verify(mLifecycleHandler).clear(anyInt());
    }

    @Test
    public void unsubscribeTest(){
        mPresenter.unsubscribe();
    }
}
