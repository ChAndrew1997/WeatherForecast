package com.chopik_andrew.weatherforecast;

import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

import java.util.ArrayList;

/**
 * Created by Andrew on 21.06.2017.
 */

public class WeatherListModel {

    public static final int FIVE_DAYS_WEATHER_MODEL = 5;
    public static final int SIXTEEN_DAYS_WEATHER_MODEL = 16;

    private String mCity;
    private int mDate;
    private double mTemperature;
    private String mDescription;
    private int mClouds;
    private int mCount;

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

        ArrayList<WeatherListModel> list = new ArrayList<>();

        for(int i = 0; i < count ; i++){

        WeatherApiManager weatherApiManager = WeatherApiManager.getInstance();

            list.add(new WeatherListModel(weatherApiManager.getCity(),
                    weatherApiManager.getDate().get(weatherApiManager.getCount() + i),
                    weatherApiManager.getTemperature().get(weatherApiManager.getCount() + i),
                    weatherApiManager.getDescription().get(weatherApiManager.getCount() + i),
                    weatherApiManager.getClouds().get(weatherApiManager.getCount() + i),
                    weatherApiManager.getCount()));
        }

        return list;
    }

    public static ArrayList<WeatherListModel> getDetailedFiveDaysModel(){
        ArrayList<WeatherListModel> detailList = new ArrayList<>();

        WeatherApiManager weatherApiManager = WeatherApiManager.getInstance();

        for (int i = 0; i < weatherApiManager.getCount(); i++){
            detailList.add(new WeatherListModel(weatherApiManager.getCity(),
                    weatherApiManager.getDate().get(i),
                    weatherApiManager.getTemperature().get(i),
                    weatherApiManager.getDescription().get(i),
                    weatherApiManager.getClouds().get(i),
                    weatherApiManager.getCount()));
        }

        return detailList;
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
