package com.chopik_andrew.weatherforecast.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.adapters.DescriptionPagerAdapter;

public class WeatherDescriptionActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "item_position";

    public static void start(Context context, int position) {
        Intent starter = new Intent(context, WeatherDescriptionActivity.class);
        starter.putExtra(EXTRA_POSITION, position);
        context.startActivity(starter);
    }

    private ViewPager mPager;
    private DescriptionPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_description);

        mPager = (ViewPager) findViewById(R.id.description_activity_pager);
        pagerAdapter = new DescriptionPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setCurrentItem(getIntent().getIntExtra(EXTRA_POSITION, 0));
    }
}
