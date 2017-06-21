package com.chopik_andrew.weatherforecast;

import android.app.Application;

import com.chopik_andrew.weatherforecast.managers.CurrentLocationManager;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

/**
 * Created by Andrew on 20.06.2017.
 */

public class App extends Application {

    private static App mInstance;

    public static synchronized App getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        CurrentLocationManager.getInstance().init(this);
        WeatherApiManager.getInstance().init();
    }
}
