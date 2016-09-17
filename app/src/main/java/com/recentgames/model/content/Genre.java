package com.recentgames.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Iskandar on 17.09.2016.
 */
public class Genre extends RealmObject {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    public Genre(){}

    public Genre(int id, @NonNull String name){
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
