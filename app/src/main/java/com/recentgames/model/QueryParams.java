package com.recentgames.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class QueryParams {

    public static final int LIMIT_COUNT = 20;

    public static final String FIELD = "field";
    public static final String LIMIT = "limit";
    public static final String FILTER = "filter";
    public static final String SORT = "sort";
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

    //todo:  add release_date and filter date at cache
    public static final String GAMES_FILED_LIST = ID + "," + NAME + "," + IMAGE;

    public static final String GAME_FILED_LIST = ID + "," + NAME + "," + IMAGES + "," + DECK + "," +
            PLATFORMS + "," + GENRES + "," + RELEASE_DATE + "," +
            RATING + "," + REVIEWS;

    public static final String REVIEW_FILED_LIST = DESCRIPTION + "," + SCORE + "," + REVIEWER + "," +
            PUBLISH_DATE;

    public static final String GAME_SORT_BY_REVIEWS_COUNT = "number_of_user_reviews:desc";

    private static final String FILTER_DATE = "original_release_date:";

    public static String getWeekFilter() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM-dd", Locale.US);

        calendar.setTimeInMillis(System.currentTimeMillis());
        String currentDate = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

        calendar.add(Calendar.DATE, -7);
        String startDate = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

        return FILTER_DATE + startDate + "|" + currentDate;
    }

    public static String getMonthFilter() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM-dd", Locale.US);

        calendar.setTimeInMillis(System.currentTimeMillis());
        String currentDate = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

        calendar.add(Calendar.MONTH, -1);
        String startDate = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

        return FILTER_DATE + startDate + "|" + currentDate;
    }

    public static String getYearFilter() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM-dd", Locale.US);

        calendar.setTimeInMillis(System.currentTimeMillis());
        String currentDate = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

        calendar.add(Calendar.YEAR, -1);
        String startDate = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

        return FILTER_DATE + startDate + "|" + currentDate;
    }

}
