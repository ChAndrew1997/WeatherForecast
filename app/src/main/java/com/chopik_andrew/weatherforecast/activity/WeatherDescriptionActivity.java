package com.chopik_andrew.weatherforecast.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chopik_andrew.weatherforecast.R;

public class WeatherDescriptionActivity extends AppCompatActivity {

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_description);

        mPager = (ViewPager) findViewById(R.id.description_activity_pager);
        /*pagerAdapter = new MyDescriptionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());*/
    }
}
