package com.recentgames.model.content;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ReviewPreview extends RealmObject {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    public ReviewPreview() {
    }

    public int getId() {
        return mId;
    }

    @Nullable
    public String getName() {
        return mName;
    }
}