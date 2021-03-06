package com.recentgames.model.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class GameDescription extends RealmObject {

    @SerializedName("id")
    private int mId;

    @SerializedName("deck")
    private String mDeck;

    @SerializedName("platforms")
    private RealmList<Platform> mPlatforms;

    @SerializedName("genres")
    private RealmList<Genre> mGenres;

    @SerializedName("image")
    private Image mImage;

    @SerializedName("images")
    private RealmList<Image> mImages;

    @SerializedName("original_release_date")
    private String mOriginalReleaseDate;

    @SerializedName("original_game_rating")
    private RealmList<Rating> mOriginalGameRating;

    @SerializedName("reviews")
    private RealmList<ReviewPreview> reviews;

    @SerializedName("similar_games")
    private RealmList<GamePreview> mSimilarGames;

    public GameDescription() {
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getDeck() {
        return mDeck;
    }

    @NonNull
    public RealmList<Platform> getPlatforms() {
        return mPlatforms;
    }

    @Nullable
    public RealmList<Genre> getGenres() {
        return mGenres;
    }

    @Nullable
    public RealmList<Image> getImages() {
        return mImages;
    }

    @Nullable
    public String getOriginalReleaseDate() {
        return mOriginalReleaseDate;
    }

    @Nullable
    public RealmList<Rating> getOriginalGameRating() {
        return mOriginalGameRating;
    }

    @Nullable
    public RealmList<ReviewPreview> getReviews() {
        return reviews;
    }

    @Nullable
    public RealmList<GamePreview> getSimilarGames() {
        return mSimilarGames;
    }

    public Image getImage() {
        return mImage;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setDeck(String deck) {
        mDeck = deck;
    }

    public void setPlatforms(RealmList<Platform> platforms) {
        mPlatforms = platforms;
    }

    public void setGenres(RealmList<Genre> genres) {
        mGenres = genres;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public void setImages(RealmList<Image> images) {
        mImages = images;
    }

    public void setOriginalReleaseDate(String originalReleaseDate) {
        mOriginalReleaseDate = originalReleaseDate;
    }

    public void setOriginalGameRating(RealmList<Rating> originalGameRating) {
        mOriginalGameRating = originalGameRating;
    }

    public void setReviews(RealmList<ReviewPreview> reviews) {
        this.reviews = reviews;
    }

    public void setSimilarGames(RealmList<GamePreview> similarGames) {
        mSimilarGames = similarGames;
    }
}
