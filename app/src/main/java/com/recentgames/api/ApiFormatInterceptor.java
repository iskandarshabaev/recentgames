package com.recentgames.api;

import com.recentgames.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class ApiFormatInterceptor implements Interceptor {

    private ApiFormatInterceptor(){}

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("format", BuildConfig.FORMAT)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }

    public static Interceptor create(){
        return new ApiFormatInterceptor();
    }
}
