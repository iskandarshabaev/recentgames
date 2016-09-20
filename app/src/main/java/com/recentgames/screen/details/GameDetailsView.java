package com.recentgames.screen.details;

import android.support.annotation.NonNull;

import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.Genre;
import com.recentgames.model.content.Image;
import com.recentgames.model.content.Platform;
import com.recentgames.model.content.ReviewPreview;
import com.recentgames.screen.LoadingView;

import java.util.List;

public interface GameDetailsView extends LoadingView {

    void showError();

    void showRating(@NonNull String ratings);

    void showReleaseDate(@NonNull String dateString);

    void showPlatforms(@NonNull List<Platform> platforms);

    void showDeck(@NonNull String text);

    void showGenres(@NonNull List<Genre> genres);

    void showReviews(@NonNull List<ReviewPreview> previews);

    void showImages(@NonNull List<Image> images);

    void showSimilarGames(@NonNull List<GamePreview> similarGames);

}