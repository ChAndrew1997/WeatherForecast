package com.chopik_andrew.weatherforecast;

import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

import java.util.ArrayList;

/**
 * Created by Andrew on 21.06.2017.
 */

public class WeatherListModel {

    private String mCity;
    private int mDate;
    private double mTemperature;
    private String mDescription;
    private int mClouds;
    private int mCount;

    private static ArrayList<WeatherListModel> mList;

    public WeatherListModel(String city, int date, double temperature, String description,
                            int clouds, int count){
        mCity = city;
        mDate = date;
        mTemperature = temperature;
        mDescription = description;
        mClouds = clouds;
        mCount = count;
    }

    public static ArrayList<WeatherListModel> getWeatherList(int count){
        if(count > 16){
            return null;
        }

        mList = new ArrayList<WeatherListModel>();

        for(int i = 0; i < count; i++){


            mList.add(new WeatherListModel(WeatherApiManager.getInstance().getCity(),
                    WeatherApiManager.getInstance().getDate().get(WeatherApiManager.getInstance().getCount() + i),
                    WeatherApiManager.getInstance().getTemperature().get(WeatherApiManager.getInstance().getCount() + i),
                    WeatherApiManager.getInstance().getDescription().get(WeatherApiManager.getInstance().getCount() + i),
                    WeatherApiManager.getInstance().getClouds().get(WeatherApiManager.getInstance().getCount() + i),
                    WeatherApiManager.getInstance().getCount()));
        }

        return mList;
    }

    public String getCity() {
        return mCity;
    }

    public int getDate() {
        return mDate;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getClouds() {
        return mClouds;
    }

    public int getCount() {
        return mCount;
    }
}
