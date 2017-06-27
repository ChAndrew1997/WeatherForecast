package com.chopik_andrew.weatherforecast.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.WeatherListModel;
import com.chopik_andrew.weatherforecast.adapters.MainPagerAdapter;
import com.chopik_andrew.weatherforecast.fragments.DescriptionFragment;
import com.chopik_andrew.weatherforecast.managers.CurrentLocationManager;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

public class MainActivity extends AppCompatActivity{

    private ViewPager mViewPager;
    private MainPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private ProgressBar mProgressBar;
    private ViewSwitcher mSwitcher;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitcher = (ViewSwitcher) findViewById(R.id.main_switcher);

        mViewPager = (ViewPager) findViewById(R.id.main_activity_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);

        mViewPager.setOffscreenPageLimit(3);

        CurrentLocationManager.getInstance().findMyLocation(locationListener);
    }

    CurrentLocationManager.FindLocationListener locationListener = new CurrentLocationManager.FindLocationListener() {
        @Override
        public void start() {
            startProgress();
        }

        @Override
        public void success() {
            WeatherApiManager.getInstance().getWeather(CurrentLocationManager.getInstance().getLatitude(),
                    CurrentLocationManager.getInstance().getLongitude(), weatherListener);
        }

        @Override
        public void failure() {
            stopProgress();
        }
    };

    WeatherApiManager.LoadWeatherListener weatherListener = new WeatherApiManager.LoadWeatherListener() {
        @Override
        public void start() {
            startProgress();
        }

        @Override
        public void success() {
            mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
            mToolBar.setTitle(WeatherApiManager.getInstance().getCity());

            mPagerAdapter.notifyDataSetChanged();

            stopProgress();
        }

        @Override
        public void failure() {
            stopProgress();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public void onFindLocationClick(MenuItem item){
        CurrentLocationManager.getInstance().findMyLocation(locationListener);
    }

    private void startProgress() {
        mSwitcher.setDisplayedChild(0);
    }

    private void stopProgress() {
        mSwitcher.setDisplayedChild(1);
    }

}
