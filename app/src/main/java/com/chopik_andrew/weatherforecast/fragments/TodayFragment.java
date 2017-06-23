package com.chopik_andrew.weatherforecast.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chopik_andrew.weatherforecast.App;
import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.WeatherListModel;
import com.chopik_andrew.weatherforecast.adapters.TodayRecyclerViewAdapter;
import com.chopik_andrew.weatherforecast.managers.CurrentLocationManager;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TodayRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_today, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.today_fragment_recycler);
        mLayoutManager = new LinearLayoutManager(App.getInstance());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }





}
