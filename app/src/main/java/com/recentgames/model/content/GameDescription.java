package com.recentgames.model.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Iskandar on 17.09.2016.
 */
public class GameDescription extends RealmObject {

    @SerializedName("id")
    private int mId;

    @SerializedName("deck")
    private String mDeck;

    @SerializedName("platforms")
    private List<Platform> mPlatforms;

    @SerializedName("genres")
    private List<Genre> mGenres;

    @SerializedName("images")
    private List<Image> mImages;

    @SerializedName("original_release_date")
    private String mOriginalReleaseDate;

    @SerializedName("original_game_rating")
    private List<Rating> mOriginalGameRating;

    @SerializedName("reviews")
    private List<ReviewPreview> reviews;

    public GameDescription(){}

    public int getId() {
        return mId;
    }

    @NonNull
    public String getDeck() {
        return mDeck;
    }

    @NonNull
    public List<Platform> getPlatforms() {
        return mPlatforms;
    }

    @NonNull
    public List<Genre> getGenres() {
        return mGenres;
    }

    @Nullable
    public List<Image> getImages() {
        return mImages;
    }

    @Nullable
    public String getOriginalReleaseDate() {
        return mOriginalReleaseDate;
    }

    @Nullable
    public List<Rating> getOriginalGameRating() {
        return mOriginalGameRating;
    }

    @Nullable
    public List<ReviewPreview> getReviews() {
        return reviews;
    }
}
