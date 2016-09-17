package com.recentgames.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface GiantBombService {

    @GET("games")
    ResponseBody getGames();
}
