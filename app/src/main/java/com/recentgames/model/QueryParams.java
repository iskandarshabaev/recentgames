package com.recentgames.model;

import rx.Observable;

/**
 * Created by Iskandar on 18.09.2016.
 */
public class QueryParams {
    public static final String FIELD = "field";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String IMAGES = "images";
    public static final String DECK = "deck";
    public static final String PLATFORMS = "platforms";
    public static final String GENRES = "genres";
    public static final String RELEASE_DATE = "original_release_date";
    public static final String RATING = "original_game_rating";
    public static final String REVIEWS = "reviews";
    public static final String SCORE = "score";
    public static final String REVIEWER = "reviewer";
    public static final String PUBLISH_DATE = "publish_date";
    public static final String DATA_REF_ID = "data-ref-id";


    public static String gamesFieldList(){
        return ID + "," + NAME + "," + IMAGE;
    }

    public static String gameFieldList(){
        return ID + "," + NAME + "," + IMAGES + "," + DECK + "," +
                PLATFORMS + "," + GENRES + "," + RELEASE_DATE + "," +
                RATING + "," + REVIEWS;
    }

    public static String reviewFieldList(){
        return ID + "," + NAME + "," + SCORE + "," + REVIEWER + "," +
                PUBLISH_DATE;
    }
}
