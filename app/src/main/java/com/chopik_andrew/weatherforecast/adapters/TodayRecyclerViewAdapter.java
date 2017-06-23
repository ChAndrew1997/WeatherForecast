package com.chopik_andrew.weatherforecast.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.WeatherListModel;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andrew on 21.06.2017.
 */

public class TodayRecyclerViewAdapter extends RecyclerView.Adapter<TodayRecyclerViewAdapter.ViewHolder> {

    private ArrayList<WeatherListModel> mList;
    private SimpleDateFormat mDateFormat;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mDateTextView;
        public TextView mTemperatureTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);

            mDateTextView = (TextView) v.findViewById(R.id.today_list_item_date);
            mTemperatureTextView = (TextView) v.findViewById(R.id.today_list_item_temperature);
            mImageView = (ImageView) v.findViewById(R.id.today_list_item_image);
        }
    }

    public TodayRecyclerViewAdapter(ArrayList<WeatherListModel> list){
        mList = list;
    }

    @Override
    public TodayRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_today_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TodayRecyclerViewAdapter.ViewHolder holder, int position) {
        mDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        final WeatherListModel model = mList.get(position);

        holder.mDateTextView.setText(mDateFormat.format(new Date(model.getDate() * 1000L)));
        holder.mTemperatureTextView.setText(Integer.toString((int) model.getTemperature() - 273));
        String description = model.getDescription();

        switch (description){
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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
