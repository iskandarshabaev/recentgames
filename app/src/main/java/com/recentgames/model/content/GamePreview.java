package com.recentgames.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class GamePreview extends RealmObject implements Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("image")
    private Image mImage;

    @SerializedName("name")
    private String mName;

    public GamePreview() {
    }

    public GamePreview(int id, @NonNull Image image, @NonNull String name) {
        mId = id;
        mImage = image;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public Image getImage() {
        return mImage;
    }

    @NonNull
    public String getName() {
        return mName;
    }
}
