package com.recentgames.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Image extends RealmObject {

    @SerializedName("medium_url")
    private String mMediumUrl;

    public Image() {
    }

    public Image(@NonNull String tinyUrl) {
        mMediumUrl = tinyUrl;
    }

    @NonNull
    public String getMediumUrl() {
        return mMediumUrl;
    }
}
