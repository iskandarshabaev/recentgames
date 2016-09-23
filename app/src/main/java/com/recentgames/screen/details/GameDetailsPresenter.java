package com.recentgames.screen.details;

import android.support.annotation.NonNull;

import com.recentgames.R;
import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Rating;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.repository.GiantBombRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;
import rx.Subscription;

public class GameDetailsPresenter {

    private final GamePreview mGame;
    private final GameDetailsView mView;
    private final LifecycleHandler mLifecycleHandler;
    private final GiantBombRepository mRepository;
    private Subscription mSubscription;

    public GameDetailsPresenter(@NonNull GamePreview game,
                                @NonNull GameDetailsView view,
                                @NonNull LifecycleHandler lifecycleHandler,
                                @NonNull GiantBombRepository repository) {
        mGame = game;
        mView = view;
        mLifecycleHandler = lifecycleHandler;
        mRepository = repository;
    }

    public void init() {
        int gameId = mGame.getId();
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
        mSubscription = mRepository.game(gameId)
                .compose(mLifecycleHandler.load(R.id.game_details_id))
                .doOnSubscribe(this::showProgress)
                .doAfterTerminate(this::hideProgress)
                .subscribe(
                        this::showGameDescription,
                        this::showException
                );

    }

    public void unsubscribe(){
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void clear() {
        mLifecycleHandler.clear(R.id.game_details_id);
    }

    private void showGameDescription(GameDescription description) {
        mView.showPoster(description.getImage());
        mView.showPlatforms(description.getPlatforms());
        mView.showGenres(description.getGenres());
        mView.showDeck(description.getDeck());
        String releaseDate = description.getOriginalReleaseDate();
        if (releaseDate != null) {
            showReleaseDate(releaseDate);
        }
        List<Rating> ratings = description.getOriginalGameRating();
        if (ratings != null) {
            showRatings(ratings);
        }
        if (description.getImages() != null) {
            mView.showImages(description.getImages());
        }
        List<ReviewPreview> reviews = description.getReviews();
        if (reviews != null) {
            mView.showReviews(reviews);
        }
        List<GamePreview> similarGames = description.getSimilarGames();
        if (similarGames != null) {
            mView.showSimilarGames(similarGames);
        }
    }

    private void showReleaseDate(@NonNull String dateString) {
        /*2016-09-13 00:00:00*/
        SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        SimpleDateFormat formatOutput = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        try {
            Date dateInput = formatInput.parse(dateString);
            String dateOutput = formatOutput.format(dateInput);
            mView.showReleaseDate(dateOutput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showRatings(@NonNull List<Rating> ratings) {
        Observable.from(ratings)
                .map(Rating::getName)
                .reduce((s, s2) -> s + ", " + s2)
                .subscribe(mView::showRating, Throwable::printStackTrace);
    }

    private void showException(Throwable throwable) {
        mView.showError();
        throwable.printStackTrace();
    }

    private void showProgress() {
        mView.showLoading();
    }

    private void hideProgress() {
        mView.hideLoading();
    }

}
