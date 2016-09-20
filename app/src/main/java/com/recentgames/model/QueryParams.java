package com.recentgames.model;

public class QueryParams {

    public static final int LIMIT = 20;

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
    public static final String DESCRIPTION = "description";
    public static final String SIMILAR_GAMES = "similar_games";
    public static final String RESOURCES = "game";

    public static final String GAMES_FILED_LIST = ID + "," + NAME + "," + IMAGE;

    public static final String GAME_FILED_LIST = ID + "," + NAME + "," + IMAGE + "," + IMAGES + "," + DECK + "," +
            PLATFORMS + "," + GENRES + "," + RELEASE_DATE + "," +
            RATING + "," + REVIEWS + "," + SIMILAR_GAMES;

    public static final String REVIEW_FILED_LIST = DESCRIPTION + "," + SCORE + "," + REVIEWER + "," +
            PUBLISH_DATE;

}
