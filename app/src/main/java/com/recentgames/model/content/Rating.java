package com.recentgames.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Rating extends RealmObject {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    public Rating(){}

    public Rating(int id, @NonNull String name){
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }
}
