package com.recentgames.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ReviewDescription extends RealmObject {

    @SerializedName("id")
    private int mId;

    @SerializedName("score")
    private int mScore;

    @SerializedName("reviewer")
    private String mReviewer;

    @SerializedName("publish_date")
    private String mPublishDate;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("deck")
    private String mDeck;

    public ReviewDescription() {
    }

    public int getScore() {
        return mScore;
    }

    @NonNull
    public String getReviewer() {
        return mReviewer;
    }

    @NonNull
    public String getPublishDate() {
        return mPublishDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public void setReviewer(String reviewer) {
        mReviewer = reviewer;
    }

    public void setPublishDate(String publishDate) {
        mPublishDate = publishDate;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDeck() {
        return mDeck;
    }

    public void setDeck(String deck) {
        mDeck = deck;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
