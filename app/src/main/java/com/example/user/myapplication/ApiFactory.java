package com.example.user.myapplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static ApiFactory instance;

    public static ApiFactory getInstance() {
        if (instance == null) {
            instance = new ApiFactory();
        }

        return instance;
    }

    public Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getHTTPClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private OkHttpClient getHTTPClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }
}
