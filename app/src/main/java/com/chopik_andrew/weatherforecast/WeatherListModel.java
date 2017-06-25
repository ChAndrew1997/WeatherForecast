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
    private double mTemperatureNight;
    private String mDescription;
    private int mClouds;
    private int mCount;
    private double mPressure;
    private double mSpeed;
    private int mHumidity;

    private WeatherListModel(int date, double temperature, String description){
        mDate = date;
        mTemperature = temperature;
        mDescription = description;
    }

    private WeatherListModel(String city, int date, double temperature, double nightTemperature, String description,
                            int clouds, double pressure, double speed, int humidity, int count){
        mCity = city;
        mDate = date;
        mTemperature = temperature;
        mTemperatureNight = nightTemperature;
        mDescription = description;
        mClouds = clouds;
        mCount = count;
        mSpeed = speed;
        mHumidity = humidity;
        mPressure = pressure;
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
                    weatherApiManager.getTemperatureNight().get(i),
                    weatherApiManager.getDescription().get(weatherApiManager.getCount() + i),
                    weatherApiManager.getClouds().get(weatherApiManager.getCount() + i),
                    weatherApiManager.getPressure().get(i),
                    weatherApiManager.getSpeed().get(i),
                    weatherApiManager.getHumidity().get(i),
                    weatherApiManager.getCount()));
        }

        return list;
    }

    public static ArrayList<WeatherListModel> getDetailedFiveDaysModel(){
        ArrayList<WeatherListModel> detailList = new ArrayList<>();

        WeatherApiManager weatherApiManager = WeatherApiManager.getInstance();

        for (int i = 0; i < weatherApiManager.getCount(); i++){
            detailList.add(new WeatherListModel(weatherApiManager.getDate().get(i),
                    weatherApiManager.getTemperature().get(i),
                    weatherApiManager.getDescription().get(i)));
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

    public double getTemperatureNight() {
        return mTemperatureNight;
    }

    public double getPressure() {
        return mPressure;
    }

    public double getSpeed() {
        return mSpeed;
    }

    public int getHumidity() {
        return mHumidity;
    }
}
