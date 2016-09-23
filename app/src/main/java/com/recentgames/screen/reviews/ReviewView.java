package com.recentgames.screen.reviews;

import com.recentgames.screen.LoadingView;

public interface ReviewView extends LoadingView {

    void showDescription(String description);

    void showDeck(String deck);

    void showScore(int rating);

    void showAuthorName(String authorName);

    void showPublishDate(String publishDate);

    void showError();

}
