package com.recentgames.screen.reviews;

import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Image;
import com.recentgames.model.content.Rating;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.repository.GiantBombRepository;
import com.recentgames.repository.RepositoryProvider;
import com.recentgames.screen.details.GameDetailsPresenter;
import com.recentgames.screen.details.GameDetailsView;
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

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ReviewsPresenterTest {

    @Mock
    private ReviewView mView;
    @Mock
    private GiantBombRepository mRepository;

    private ReviewPresenter mPresenter;
    private ReviewPreview mReview;
    private LifecycleHandler mLifecycleHandler;

    @Before
    public void setUp() throws Exception {
        RxSchedulersHooks.immediate();
        mReview = new ReviewPreview(45, "dasdasd");
        mLifecycleHandler = new MockLifecycleHandler();
        mView = mock(ReviewView.class);
        mRepository = mock(GiantBombRepository.class);
        RepositoryProvider.setGiantBombRepository(mRepository);
        mPresenter = new ReviewPresenter(mView,
                RepositoryProvider.provideGiantBombRepository(), mLifecycleHandler);
    }

    @Test
    public void initTest() throws Exception {
        ReviewDescription reviewDescription = new ReviewDescription();
        reviewDescription.setDeck("deck");
        when(mRepository.review(mReview.getId())).thenReturn(Observable.just(reviewDescription));
        mPresenter.loadContent(mReview.getId());
        verify(mView).showDescription(reviewDescription.getDescription());
        verify(mView).showScore(reviewDescription.getScore());
        verify(mView).showDeck(reviewDescription.getDeck());
        verify(mView).showPublishDate(reviewDescription.getPublishDate());
        verify(mView).showAuthorName(reviewDescription.getReviewer());
    }

    @Test
    public void initShowErrorTest() throws Exception {
        when(mRepository.review(mReview.getId()))
                .thenReturn(Observable.error(new Exception()));
        mPresenter.loadContent(mReview.getId());
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
