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
 * Created by Andrew on 18.06.2017.
 */

public class DescriptionRecyclerAdapter extends RecyclerView.Adapter<DescriptionRecyclerAdapter.ViewHolder> {

    private ArrayList<WeatherListModel> mList;
    private SimpleDateFormat mDateFormat;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public ImageView image;
        public TextView temp;

        public ViewHolder(View v) {
            super(v);
            time = (TextView) v.findViewById(R.id.description_recycler_time);
            image = (ImageView) v.findViewById(R.id.description_recycler_image);
            temp = (TextView) v.findViewById(R.id.description_recycler_temp);
        }
    }

    public DescriptionRecyclerAdapter(ArrayList<WeatherListModel> list) {
        mList = list;
    }

    @Override
    public DescriptionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_description_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mDateFormat = new SimpleDateFormat("H:mm");

        final WeatherListModel model = mList.get(position);

        String date = mDateFormat.format(new Date(model.getDate() * 1000L));
        String temp = Integer.toString((int)model.getTemperature() - 273);

        holder.time.setText(date);
        holder.temp.setText(temp);
        String desc = model.getDescription();

        switch (desc){
            case WeatherApiManager.WEATHER_TYPE_RAIN:
                holder.image.setImageResource(R.drawable.rain);
                break;
            case WeatherApiManager.WEATHER_TYPE_CLOUDS:
                holder.image.setImageResource(R.drawable.cloudy);
                break;
            case WeatherApiManager.WEATHER_TYPE_CLEAR:
                holder.image.setImageResource(R.drawable.sun);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
