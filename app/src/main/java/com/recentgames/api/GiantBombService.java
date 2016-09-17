package com.recentgames.api;

import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.model.response.GiantBombResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GiantBombService {

    @GET("game/{gameId}")
    Observable<GiantBombResponse<GameDescription>> game(@Path("movieId") int gameId,
                                                        @Query("field_list") String fieldList);

    @GET("games/")
    Observable<GiantBombResponse<List<GamePreview>>> games(@Query("field_list") String fieldList,
                                                           @Query("limit") int limit,
                                                           @Query("offset") int offset);

    @GET("review/{reviewId}")
    Observable<GiantBombResponse<ReviewDescription>> review(@Path("reviewId") int reviewId,
                                                            @Query("field_list") String fieldList);
}
