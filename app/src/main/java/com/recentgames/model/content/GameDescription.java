package com.recentgames.model.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    @NonNull
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
}
