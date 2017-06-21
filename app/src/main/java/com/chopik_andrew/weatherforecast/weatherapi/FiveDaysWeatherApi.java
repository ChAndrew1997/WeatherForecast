package com.chopik_andrew.weatherforecast.weatherapi;

import com.chopik_andrew.weatherforecast.weatherapi.fivedayspojo.FiveDaysWeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andrew on 20.06.2017.
 */

public interface FiveDaysWeatherApi {
    @GET("/data/2.5/forecast")
    Call<FiveDaysWeatherModel> getData(@Query("lat") double latitude, @Query("lon") double longitude, @Query("APPID") String key);
}
