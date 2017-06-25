package com.chopik_andrew.weatherforecast.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.adapters.DescriptionPagerAdapter;

public class WeatherDescriptionActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "item_position";

    private ViewPager mPager;
    private DescriptionPagerAdapter pagerAdapter;
    private Toolbar mToolbar;

    public static void start(Context context, int position) {
        Intent starter = new Intent(context, WeatherDescriptionActivity.class);
        starter.putExtra(EXTRA_POSITION, position);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_description);

        mToolbar = (Toolbar) findViewById(R.id.description_toolbar);
        mPager = (ViewPager) findViewById(R.id.description_activity_pager);

        pagerAdapter = new DescriptionPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setCurrentItem(getIntent().getIntExtra(EXTRA_POSITION, 0));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
