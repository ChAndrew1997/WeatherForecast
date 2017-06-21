package com.chopik_andrew.weatherforecast.weatherapi;

import com.chopik_andrew.weatherforecast.weatherapi.sixteendayspojo.SixteenDaysWeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andrew on 20.06.2017.
 */

public interface SixteenDaysWeatherApi {
    @GET("/data/2.5/forecast/daily")
    Call<SixteenDaysWeatherModel> getData(@Query("lat") double latitude, @Query("lon") double longitude,
                                          @Query("cnt") int count, @Query("APPID") String key);
}
