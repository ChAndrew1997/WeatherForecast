package com.chopik_andrew.weatherforecast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.WeatherListModel;
import com.chopik_andrew.weatherforecast.activity.WeatherDescriptionActivity;
import com.chopik_andrew.weatherforecast.adapters.DescriptionRecyclerAdapter;
import com.chopik_andrew.weatherforecast.managers.CurrentLocationManager;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {

    private static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private static final String ARGUMENT_MAIN_PAGE_NUMBER = "arg_main_page_number";

    private int mPageNumber;
    private int mMainPageNumber;

    private TextView mDate;
    private ImageView mImage;
    private TextView mTemperature;
    private TextView mTemperatureNight;
    private TextView mDescription;
    private TextView mClouds;
    private TextView mHumidity;
    private TextView mSpeed;
    private TextView mPressure;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresh;

    private ArrayList<WeatherListModel> mList;

    private SimpleDateFormat mDateFormat;

    public static DescriptionFragment newInstance(int page, int mainPage) {
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt(ARGUMENT_MAIN_PAGE_NUMBER, mainPage);
        descriptionFragment.setArguments(arguments);
        return descriptionFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        mMainPageNumber = getArguments().getInt(ARGUMENT_MAIN_PAGE_NUMBER);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_description, null);

        mDate = (TextView) view.findViewById(R.id.description_date);
        mImage = (ImageView) view.findViewById(R.id.description_image);
        mTemperature = (TextView) view.findViewById(R.id.description_temp);
        mTemperatureNight = (TextView) view.findViewById(R.id.description_temp_night);
        mDescription = (TextView) view.findViewById(R.id.description);
        mClouds = (TextView) view.findViewById(R.id.clouds);
        mHumidity = (TextView) view.findViewById(R.id.humidity);
        mPressure = (TextView) view.findViewById(R.id.pressure);
        mSpeed = (TextView) view.findViewById(R.id.speed);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.fragment_refresh);

        if (mMainPageNumber == MainListFragment.SIXTEEN_WEATHER_VIEW_TYPE){
            mList = WeatherListModel.getWeatherList(WeatherListModel.SIXTEEN_DAYS_WEATHER_MODEL);
        } else{
            mList = WeatherListModel.getWeatherList(WeatherListModel.FIVE_DAYS_WEATHER_MODEL);
        }

        final WeatherListModel model = mList.get(mPageNumber);

        mDateFormat = new SimpleDateFormat("E");

        drawFragment(model);

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(llm);
        DescriptionRecyclerAdapter  adapter = new DescriptionRecyclerAdapter(divideFiveDaysListModel(WeatherListModel.getDetailedFiveDaysModel()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.hasFixedSize();

        mRefresh.setColorSchemeResources(R.color.accent);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(true);
                WeatherApiManager.getInstance().getWeather(CurrentLocationManager.getInstance().getLatitude(),
                        CurrentLocationManager.getInstance().getLongitude(), weatherListener);
            }
        });

        return view;
    }

    WeatherApiManager.LoadWeatherListener weatherListener = new WeatherApiManager.LoadWeatherListener() {
        @Override
        public void start() {

        }

        @Override
        public void success() {
            if (mMainPageNumber == MainListFragment.SIXTEEN_WEATHER_VIEW_TYPE){
                mList = WeatherListModel.getWeatherList(WeatherListModel.SIXTEEN_DAYS_WEATHER_MODEL);
            } else{
                mList = WeatherListModel.getWeatherList(WeatherListModel.FIVE_DAYS_WEATHER_MODEL);
            }
            final WeatherListModel model = mList.get(mPageNumber);

            drawFragment(model);

            DescriptionRecyclerAdapter  adapter = new DescriptionRecyclerAdapter(divideFiveDaysListModel(WeatherListModel.getDetailedFiveDaysModel()));
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            mRefresh.setRefreshing(false);
        }

        @Override
        public void failure() {

        }
    };

    private ArrayList<WeatherListModel> divideFiveDaysListModel(ArrayList<WeatherListModel> fiveDays){
        mDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        ArrayList<WeatherListModel> oneDay = new ArrayList<>();
        int page = mPageNumber;

        for (int i = 0; i < fiveDays.size(); i++){
            if(page == 0) {
                oneDay.add(fiveDays.get(i));
            }
            if(fiveDays.size() == i + 1){
                break;
            }
            if (!mDateFormat.format(new Date(fiveDays.get(i).getDate() * 1000L)).equals(mDateFormat.format(new Date(fiveDays.get(i + 1).getDate() * 1000L)))){
                page--;
                if (page < 0)
                    break;
            }
        }
        return oneDay;
    }

    public void drawFragment(WeatherListModel model){
        String pageDate = mDateFormat.format(new Date(model.getDate() * 1000L));
        String pageTemp = Integer.toString((int) model.getTemperature() - 273);
        String pageClouds = Integer.toString(model.getClouds());
        String pageTempNight = Integer.toString((int) model.getTemperatureNight() - 273);
        String pagePressure = Double.toString(model.getPressure());
        String pageHumidity = Integer.toString(model.getHumidity());
        String pageSpeed = Double.toString(model.getSpeed());

        mTemperature.setText(pageTemp);
        mTemperatureNight.setText(pageTempNight);
        mClouds.setText(getString(R.string.cloudiness) + "  " + pageClouds + "%");
        mSpeed.setText(getString(R.string.wind_speed) + "  " + pageSpeed + "м/c");
        mHumidity.setText(getString(R.string.humidity) + "  " + pageHumidity + "%");
        mPressure.setText(getString(R.string.pressure) + "  " + pagePressure + "гПа");

        switch (model.getDescription()){
            case WeatherApiManager.WEATHER_TYPE_RAIN:
                mImage.setImageResource(R.drawable.rain_huge);
                mDescription.setText(getString(R.string.rain));
                break;
            case WeatherApiManager.WEATHER_TYPE_CLEAR:
                mImage.setImageResource(R.drawable.sun_huge);
                mDescription.setText(getString(R.string.sunny));
                break;
            case WeatherApiManager.WEATHER_TYPE_CLOUDS:
                mImage.setImageResource(R.drawable.cloudy_huge);
                mDescription.setText(getString(R.string.clouds));
                break;
        }
        switch (pageDate){
            case "пн":
                mDate.setText(getString(R.string.mon));
                break;
            case "вт":
                mDate.setText(getString(R.string.tue));
                break;
            case "ср":
                mDate.setText(getString(R.string.wed));
                break;
            case "чт":
                mDate.setText(getString(R.string.thu));
                break;
            case "пт":
                mDate.setText(getString(R.string.fri));
                break;
            case "сб":
                mDate.setText(getString(R.string.sat));
                break;
            case "вс":
                mDate.setText(getString(R.string.sun));
                break;
        }
    }

}
