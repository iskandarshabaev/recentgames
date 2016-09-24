package com.recentgames.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.recentgames.App;
import com.recentgames.repository.RepositoryProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;
import okhttp3.Response;

public class RequestsHandler {
    private final Map<String, String> mResponsesMap = new HashMap<>();

    public RequestsHandler() {
        mResponsesMap.put("/game", "game.json");
        mResponsesMap.put("/review", "review.json");
        mResponsesMap.put("/search", "game_search.json");
    }

    private static volatile boolean sGenerateException;

    public static synchronized void  setGenerateException(boolean value){
        sGenerateException = value;
    }

    public boolean shouldIntercept(@NonNull String path) {
        Set<String> keys = mResponsesMap.keySet();
        for (String interceptUrl : keys) {
            if (path.contains(interceptUrl)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    public Response proceed(@NonNull Request request, @NonNull String path, @NonNull String query) {
        if(sGenerateException){
            return OkHttpResponse.error(request, 1, "");
        }
        Set<String> keys = mResponsesMap.keySet();
        for (String interceptUrl : keys) {
            if(path.contains("game/") || path.contains("search/")){
                return proceedGameRequest(request, path, query);
            }else if(path.contains("review/")){
                return proceedReviewRequest(request, path, query);
            }else if (path.contains(interceptUrl)) {
                String mockResponsePath = mResponsesMap.get(interceptUrl);
                return createResponseFromAssets(request, mockResponsePath);
            }
        }

        return OkHttpResponse.error(request, 500, "Incorrectly intercepted request");
    }

    @NonNull
    private Response createResponseFromAssets(@NonNull Request request, @NonNull String assetPath) {
        Context context = App.getContext();
        try {
            final InputStream stream = context.getAssets().open(assetPath);
            try {
                return OkHttpResponse.success(request, stream);
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            return OkHttpResponse.error(request, 500, e.getMessage());
        }
    }

    private Response proceedGameRequest(@NonNull Request request, @NonNull String path, @NonNull String query){
        if(path.contains("game/36067")){
            return createResponseFromAssets(request, "game.json");
        } else if (path.contains("search/") && query.contains("gta")) {
            return createResponseFromAssets(request, "game_search.json");
        } else {
            return createResponseFromAssets(request, "game_not_found.json");
        }
    }

    private Response proceedReviewRequest(@NonNull Request request, @NonNull String path, @NonNull String query){
        if(path.contains("review/45")){
            return createResponseFromAssets(request, "review.json");
        } else {
            return createResponseFromAssets(request, "review_not_found.json");
        }
    }
}
