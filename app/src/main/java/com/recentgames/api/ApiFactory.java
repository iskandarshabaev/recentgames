package com.recentgames.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recentgames.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiFactory {

    private static final Gson GSON_DATE = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    private static OkHttpClient sClient;
    //private static OkHttpClient sClient;

    private static GiantBombService sService;

    @NonNull
    public static GiantBombService getGiantBombService() {
        GiantBombService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = createService();
                }
            }
        }
        return service;
    }

    @NonNull
    private static GiantBombService createService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(OkHttpProvider.provideClient())
                .addConverterFactory(GsonConverterFactory.create(GSON_DATE))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(GiantBombService.class);
    }

    /*@NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }*/

   /* @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                    .addInterceptor(ApiKeyInterceptor.create())
                    .addInterceptor(ApiFormatInterceptor.create())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
    }*/
}
