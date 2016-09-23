package com.recentgames.screen.reviews;

import android.support.annotation.NonNull;

import com.recentgames.R;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.repository.GiantBombRepository;

import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Subscription;

public class ReviewPresenter {

    private ReviewView mView;
    private GiantBombRepository mRepository;
    private LifecycleHandler mLifecycleHandler;
    private Subscription mSubscription;

    public ReviewPresenter(@NonNull ReviewView view,
                           @NonNull GiantBombRepository repository,
                           @NonNull LifecycleHandler lifecycleHandler) {
        mView = view;
        mRepository = repository;
        mLifecycleHandler = lifecycleHandler;
    }

    public void loadContent(int reviewId) {
        unsubscribe();
        mSubscription = mRepository.review(reviewId)
                .compose(mLifecycleHandler.load(R.id.review_id))
                .doOnSubscribe(this::showProgress)
                .doAfterTerminate(this::hideProgress)
                .subscribe(this::showReview, this::showError);
    }

    private void showReview(ReviewDescription reviewDescription) {
        mView.showAuthorName(reviewDescription.getReviewer());
        mView.showPublishDate(reviewDescription.getPublishDate());
        mView.showDeck(reviewDescription.getDeck());
        mView.showScore(reviewDescription.getScore());
        mView.showDescription(reviewDescription.getDescription());
    }

    private void showError(Throwable throwable) {
        throwable.printStackTrace();
        mView.showError();
    }

    private void showProgress() {
        mView.showLoading();
    }

    private void hideProgress() {
        mView.hideLoading();
    }

    public void unsubscribe(){
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
    }

    public void clear(){
        mLifecycleHandler.clear(R.id.review_id);
    }
}
