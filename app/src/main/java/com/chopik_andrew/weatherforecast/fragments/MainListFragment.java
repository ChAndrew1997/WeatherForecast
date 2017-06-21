package com.chopik_andrew.weatherforecast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopik_andrew.weatherforecast.App;
import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.WeatherListModel;
import com.chopik_andrew.weatherforecast.adapters.MainRecyclerViewAdapter;
import com.chopik_andrew.weatherforecast.managers.CurrentLocationManager;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

public class MainListFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private int mPageNumber;
    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mRecyclerViewAdapter;

    public static MainListFragment newInstance(int page) {
        MainListFragment listFragment = new MainListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        listFragment.setArguments(arguments);
        return listFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance()));

        CurrentLocationManager.getInstance().findMyLocation(locationListener);

        return view;
    }

    CurrentLocationManager.FindLocationListener locationListener = new CurrentLocationManager.FindLocationListener() {
        @Override
        public void start() {

        }

        @Override
        public void success() {
            WeatherApiManager.getInstance().getWeather(CurrentLocationManager.getInstance().getLatitude(),
                    CurrentLocationManager.getInstance().getLongitude(), weatherListener);
        }

        @Override
        public void failure() {

        }
    };

    WeatherApiManager.LoadWeatherListener weatherListener = new WeatherApiManager.LoadWeatherListener() {
        @Override
        public void start() {

        }

        @Override
        public void success() {
            if(mPageNumber == 1){
                mRecyclerViewAdapter = new MainRecyclerViewAdapter(WeatherListModel.getWeatherList(5));
            } else{
                mRecyclerViewAdapter = new MainRecyclerViewAdapter(WeatherListModel.getWeatherList(16));
            }
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
            mRecyclerViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void failure() {

        }
    };

}
