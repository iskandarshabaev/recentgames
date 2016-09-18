package com.recentgames.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ReviewDescription extends RealmObject {

    @SerializedName("score")
    private int mScore;

    @SerializedName("reviewer")
    private String mReviewer;

    @SerializedName("publish_date")
    private String mPublishDate;

    @SerializedName("description")
    private String mDescription;

    public ReviewDescription(){}

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
}
