package com.chopik_andrew.weatherforecast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chopik_andrew.weatherforecast.App;
import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.WeatherListModel;
import com.chopik_andrew.weatherforecast.activity.WeatherDescriptionActivity;
import com.chopik_andrew.weatherforecast.fragments.MainListFragment;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andrew on 21.06.2017.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private ArrayList<WeatherListModel> mList;
    private SimpleDateFormat mDateFormat;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mDateTextView;
        public TextView mTemperatureTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);

            mDateTextView = (TextView) v.findViewById(R.id.main_list_item_date);
            mTemperatureTextView = (TextView) v.findViewById(R.id.main_list_item_temperature);
            mImageView = (ImageView) v.findViewById(R.id.main_list_item_image);
        }
    }

    public MainRecyclerViewAdapter(Context context, ArrayList<WeatherListModel> list, RecyclerView recyclerView) {
        mList = list;
        mRecyclerView = recyclerView;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_main_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        final WeatherListModel model = mList.get(position);

        holder.mDateTextView.setText(mDateFormat.format(new Date(model.getDate() * 1000L)));
        holder.mTemperatureTextView.setText(Integer.toString((int) model.getTemperature() - 273));
        String description = model.getDescription();

        switch (description) {
            case WeatherApiManager.WEATHER_TYPE_RAIN:
                holder.mImageView.setImageResource(R.drawable.rain);
                break;
            case WeatherApiManager.WEATHER_TYPE_CLOUDS:
                holder.mImageView.setImageResource(R.drawable.cloudy);
                break;
            case WeatherApiManager.WEATHER_TYPE_CLEAR:
                holder.mImageView.setImageResource(R.drawable.sun);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherDescriptionActivity.start(mContext, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
