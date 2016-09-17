package com.recentgames.model.content;

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
    private String mPlatforms;

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

    public String getDeck() {
        return mDeck;
    }

    public String getPlatforms() {
        return mPlatforms;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public String getOriginalReleaseDate() {
        return mOriginalReleaseDate;
    }

    public List<Rating> getOriginalGameRating() {
        return mOriginalGameRating;
    }

    public List<ReviewPreview> getReviews() {
        return reviews;
    }
}
