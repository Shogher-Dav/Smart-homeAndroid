package com.example.user.myapplication;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerInterface {

    @GET("/api/climate/{temp}")
    Call<JsonObject> setTemperature(
            @Path("temp") int temp,
            @Query("token") String token
    );

    @GET("api/climate/{temp}/{start}/{end}")
    Call<JsonObject> scheduleClimate(
            @Path("temp") int temp,
            @Path("start") String start,
            @Path("end") String end,
            @Query("token") String token
    );

    @GET("/api/light/{number}/{status}")
    Call<JsonObject> setLight(
            @Path("number") int number,
            @Path("status") int status,
            @Query("token") String token
    );

    @GET("/api/led/{number}/{status}")
    Call<JsonObject> setLedColor(
            @Path("number") int number,
            @Path("status") int status,
            @Query("token") String token
    );

    @GET("/api/led/random")
    Call<JsonObject> changeRandomLed(
            @Query("token") String token
    );

    @GET("/api/curtain/1/{status}")
    Call<JsonObject> changeCurtainState(
            @Path("status") int status,
            @Query("token") String token
    );
}
