package com.recentgames.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Platform extends RealmObject {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    public Platform() {
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }
}
