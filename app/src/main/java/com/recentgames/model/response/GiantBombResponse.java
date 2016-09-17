package com.recentgames.model.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iskandar on 17.09.2016.
 */
public class GiantBombResponse<T> {

    @SerializedName("error")
    private String mError;

    @SerializedName("limit")
    private int mLimit;

    @SerializedName("offset")
    private int mOffset;

    @SerializedName("number_of_page_results")
    private int mNumberOfPageResults;

    @SerializedName("number_of_total_results")
    private int mNumberOfTotalResults;

    @SerializedName("status_code")
    private int mStatusCode;

    @SerializedName("results")
    private T mResults;

    @NonNull
    public String getError() {
        return mError;
    }

    public int getLimit() {
        return mLimit;
    }

    public int getOffset() {
        return mOffset;
    }

    public int getNumberOfPageResults() {
        return mNumberOfPageResults;
    }

    public int getNumberOfTotalResults() {
        return mNumberOfTotalResults;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    @Nullable
    public T getResults() {
        return mResults;
    }
}
