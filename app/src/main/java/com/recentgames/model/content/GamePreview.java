package com.recentgames.model.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GamePreview extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private int mId;

    @SerializedName("image")
    private Image mImage;

    @SerializedName("name")
    private String mName;

    @SerializedName("original_release_date")
    private Date mReleaseDate;

    public GamePreview() {
    }

    public GamePreview(int id, @NonNull Image image, @NonNull String name, @NonNull Date releaseDate) {
        mId = id;
        mImage = image;
        mName = name;
        mReleaseDate = releaseDate;
    }

    public int getId() {
        return mId;
    }

    @Nullable
    public Image getImage() {
        return mImage;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public Date getReleaseDate() {
        return mReleaseDate;
    }
}
