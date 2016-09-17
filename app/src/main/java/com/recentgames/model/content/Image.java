package com.recentgames.model.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Iskandar on 17.09.2016.
 */
public class Image extends RealmObject {

    @SerializedName("tiny_url")
    private String mTinyUrl;

    public Image(){}

    public Image(@NonNull String tinyUrl){
        mTinyUrl = tinyUrl;
    }

    @Nullable
    public String getTinyUrl() {
        return mTinyUrl;
    }
}
