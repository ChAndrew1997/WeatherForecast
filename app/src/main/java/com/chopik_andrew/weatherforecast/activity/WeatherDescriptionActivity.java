package com.chopik_andrew.weatherforecast.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.WeatherListModel;
import com.chopik_andrew.weatherforecast.adapters.DescriptionPagerAdapter;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDescriptionActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "item_position";
    public static final String EXTRA_PAGE_NUMBER = "main_page_number";

    private ViewPager mPager;
    private DescriptionPagerAdapter mPagerAdapter;
    private Toolbar mToolbar;
    private SimpleDateFormat mDateFormat;
    private int mMainPageNumber;

    public static void start(Context context, int position, int pageNumber) {
        Intent starter = new Intent(context, WeatherDescriptionActivity.class);
        starter.putExtra(EXTRA_POSITION, position);
        starter.putExtra(EXTRA_PAGE_NUMBER, pageNumber);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_description);

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        mMainPageNumber = getIntent().getIntExtra(EXTRA_PAGE_NUMBER, 1);

        mToolbar = (Toolbar) findViewById(R.id.description_toolbar);
        mPager = (ViewPager) findViewById(R.id.description_activity_pager);
        mDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String title = mDateFormat.format(new Date(WeatherApiManager.getInstance().getDate().get(WeatherApiManager.getInstance().getCount() + position) * 1000L));
        mToolbar.setTitle(title);

        mPagerAdapter = new DescriptionPagerAdapter(getSupportFragmentManager(), mMainPageNumber);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(position);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String title = mDateFormat.format(new Date(WeatherApiManager.getInstance().getDate().get(WeatherApiManager.getInstance().getCount() + position) * 1000L));
                mToolbar.setTitle(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    public void onShareClick(MenuItem item){
        int pagerPage = mPager.getCurrentItem();
        WeatherListModel pageModel = WeatherListModel.getWeatherList(WeatherListModel.SIXTEEN_DAYS_WEATHER_MODEL).get(pagerPage);

        String date = mDateFormat.format(new Date(pageModel.getDate() * 1000L));
        String city = pageModel.getCity();
        String url = WeatherApiManager.BASE_URL;
        String temp = Integer.toString((int) pageModel.getTemperature() - 273);
        String tempNight = Integer.toString((int) pageModel.getTemperatureNight() - 273);
        String desc = pageModel.getDescription();
        String message = new StringBuilder().append("Погода ").append(date).append(" ").append(city).append("\n").append("Днем: ")
                .append(temp).append(" ночью: ").append(tempNight).append("\n").append(desc).append("\n").append(url).toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        String chosenTitle = "Send...";
        Intent chosenIntent = Intent.createChooser(intent, chosenTitle);
        startActivity(chosenIntent);
    }
}
