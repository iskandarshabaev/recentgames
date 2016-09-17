package com.recentgames.model.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Iskandar on 17.09.2016.
 */
public class ReviewDescription extends RealmObject {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("score")
    private int mScore;

    @SerializedName("reviewer")
    private String mReviewer;

    @SerializedName("publish_date")
    private String mPublishDate;

    public ReviewDescription(){}

    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
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
}
