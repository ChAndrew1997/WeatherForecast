package com.chopik_andrew.weatherforecast.managers;

import com.chopik_andrew.weatherforecast.weatherapi.FiveDaysWeatherApi;
import com.chopik_andrew.weatherforecast.weatherapi.SixteenDaysWeatherApi;
import com.chopik_andrew.weatherforecast.weatherapi.fivedayspojo.FiveDaysWeatherModel;
import com.chopik_andrew.weatherforecast.weatherapi.sixteendayspojo.SixteenDaysWeatherModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andrew on 20.06.2017.
 */

public class WeatherApiManager {

    public static final String BASE_URL = "http://api.openweathermap.org/";
    private static final String API_KEY = "a84d20ba16e63145fec0b712d6547707";
    public static final String WEATHER_TYPE_RAIN = "Rain";
    public static final String WEATHER_TYPE_CLEAR = "Clear";
    public static final String WEATHER_TYPE_CLOUDS = "Clouds";

    private static WeatherApiManager mInstance;
    private FiveDaysWeatherApi mFiveDaysWeatherAPI;
    private SixteenDaysWeatherApi mSixteenDaysWeatherAPI;
    private Retrofit mRetrofit;

    private String mCity;
    private int mCount;
    private ArrayList<Integer> mDate;
    private ArrayList<Double> mTemperature;
    private ArrayList<String> mDescription;
    private ArrayList<Integer> mClouds;
    private ArrayList<Double> mTemperatureNight;
    private ArrayList<Double> mPressure;
    private ArrayList<Integer> mHumidity;
    private ArrayList<Double> mSpeed;

    public static synchronized WeatherApiManager getInstance() {
        if (mInstance == null) {
            mInstance = new WeatherApiManager();
        }

        return mInstance;
    }

    private WeatherApiManager() {

    }

    public void init() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mFiveDaysWeatherAPI = mRetrofit.create(FiveDaysWeatherApi.class);
        mSixteenDaysWeatherAPI = mRetrofit.create(SixteenDaysWeatherApi.class);
    }

    public void getWeather(final double lat, final double lon, final LoadWeatherListener listener) {

        mDate = new ArrayList<>();
        mTemperature = new ArrayList<>();
        mDescription = new ArrayList<>();
        mClouds = new ArrayList<>();
        mTemperatureNight = new ArrayList<>();
        mPressure = new ArrayList<>();
        mHumidity = new ArrayList<>();
        mSpeed = new ArrayList<>();

        if (listener != null)
            listener.start();

        mFiveDaysWeatherAPI.getData(lat, lon, API_KEY).enqueue(new Callback<FiveDaysWeatherModel>() {
            @Override
            public void onResponse(Call<FiveDaysWeatherModel> call, Response<FiveDaysWeatherModel> response) {
                mCity = response.body().getCity().getName();
                mCount = response.body().getCnt();
                List<com.chopik_andrew.weatherforecast.weatherapi.fivedayspojo.List> fiveList = response.body().getList();
                for (int i = 0; i < fiveList.size(); i++) {
                    com.chopik_andrew.weatherforecast.weatherapi.fivedayspojo.List fiveListModel = fiveList.get(i);
                    mDate.add(fiveListModel.getDt());
                    mTemperature.add(fiveListModel.getMain().getTemp());
                    mDescription.add(fiveListModel.getWeather().get(0).getMain());
                    mClouds.add(fiveListModel.getClouds().getAll());
                }

                mSixteenDaysWeatherAPI.getData(lat, lon, 16, API_KEY).enqueue(new Callback<SixteenDaysWeatherModel>() {
                    @Override
                    public void onResponse(Call<SixteenDaysWeatherModel> call, Response<SixteenDaysWeatherModel> response) {
                        List<com.chopik_andrew.weatherforecast.weatherapi.sixteendayspojo.List> sixteenList = response.body().getList();
                        for (int i = 0; i < sixteenList.size(); i++) {
                            com.chopik_andrew.weatherforecast.weatherapi.sixteendayspojo.List sixteenListModel = sixteenList.get(i);
                            mDate.add(sixteenListModel.getDt());
                            mTemperature.add(sixteenListModel.getTemp().getDay());
                            mDescription.add(sixteenListModel.getWeather().get(0).getMain());
                            mClouds.add(sixteenListModel.getClouds());
                            mTemperatureNight.add(sixteenListModel.getTemp().getNight());
                            mSpeed.add(sixteenListModel.getSpeed());
                            mHumidity.add(sixteenListModel.getHumidity());
                            mPressure.add(sixteenListModel.getPressure());
                        }
                        if (listener != null)
                            listener.success();
                    }

                    @Override
                    public void onFailure(Call<SixteenDaysWeatherModel> call, Throwable t) {
                        if (listener != null)
                            listener.failure();
                    }
                });
            }

            @Override
            public void onFailure(Call<FiveDaysWeatherModel> call, Throwable t) {
                if (listener != null)
                    listener.failure();
            }
        });
    }

    public String getCity() {
        return mCity;
    }

    public ArrayList<Integer> getDate() {
        return mDate;
    }

    public ArrayList<Double> getTemperature() {
        return mTemperature;
    }

    public ArrayList<String> getDescription() {
        return mDescription;
    }

    public ArrayList<Integer> getClouds() {
        return mClouds;
    }

    public int getCount() {
        return mCount;
    }

    public ArrayList<Double> getTemperatureNight() {
        return mTemperatureNight;
    }

    public ArrayList<Double> getPressure() {
        return mPressure;
    }

    public ArrayList<Integer> getHumidity() {
        return mHumidity;
    }

    public ArrayList<Double> getSpeed() {
        return mSpeed;
    }

    public interface LoadWeatherListener {
        void start();

        void success();

        void failure();
    }
}
