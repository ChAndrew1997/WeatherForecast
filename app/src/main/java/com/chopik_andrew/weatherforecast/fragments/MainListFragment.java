package com.chopik_andrew.weatherforecast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

    public static final int TODAY_WEATHER_VIEW_TYPE = 1;
    public static final int FIVE_WEATHER_VIEW_TYPE = 2;
    public static final int SIXTEEN_WEATHER_VIEW_TYPE = 3;

    private int mPageNumber;
    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mRecyclerViewAdapter;
    private SwipeRefreshLayout mRefresh;

    public static MainListFragment newInstance(int type) {
        MainListFragment listFragment = new MainListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, type);
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

        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.list_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance()));
        mRecyclerView.hasFixedSize();

        switch (mPageNumber){
            case FIVE_WEATHER_VIEW_TYPE:
                mRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(),
                        WeatherListModel.getWeatherList(WeatherListModel.FIVE_DAYS_WEATHER_MODEL), mPageNumber);
                break;
            case SIXTEEN_WEATHER_VIEW_TYPE:
                mRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(),
                        WeatherListModel.getWeatherList(WeatherListModel.SIXTEEN_DAYS_WEATHER_MODEL), mPageNumber);
                break;

        }

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

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
            switch (mPageNumber){
                case FIVE_WEATHER_VIEW_TYPE:
                    mRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(),
                            WeatherListModel.getWeatherList(WeatherListModel.FIVE_DAYS_WEATHER_MODEL), mPageNumber);
                    break;
                case SIXTEEN_WEATHER_VIEW_TYPE:
                    mRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(),
                            WeatherListModel.getWeatherList(WeatherListModel.SIXTEEN_DAYS_WEATHER_MODEL), mPageNumber);
                    break;

            }
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
            mRecyclerViewAdapter.notifyDataSetChanged();

            mRefresh.setRefreshing(false);
        }

        @Override
        public void failure() {

        }
    };

}
