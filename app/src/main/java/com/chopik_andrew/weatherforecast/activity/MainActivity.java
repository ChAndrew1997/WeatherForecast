package com.chopik_andrew.weatherforecast.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.adapters.MainPagerAdapter;
import com.chopik_andrew.weatherforecast.managers.CurrentLocationManager;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private MainPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.main_activity_pager);



        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
        mViewPager.setOffscreenPageLimit(3);
    }


}
