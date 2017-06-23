package com.chopik_andrew.weatherforecast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopik_andrew.weatherforecast.R;
import com.chopik_andrew.weatherforecast.WeatherListModel;
import com.chopik_andrew.weatherforecast.adapters.DescriptionRecyclerAdapter;
import com.chopik_andrew.weatherforecast.managers.WeatherApiManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {

    private static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private int mPageNumber;

    private TextView mDate;
    private ImageView mImage;
    private TextView mTemp;
    private TextView mDesc;
    private TextView mClouds;
    private RecyclerView mRecyclerView;
    private ArrayList<WeatherListModel> mList;

    private SimpleDateFormat mDateFormat;

    public static DescriptionFragment newInstance(int page) {
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        descriptionFragment.setArguments(arguments);
        return descriptionFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        mList = WeatherListModel.getWeatherList(WeatherListModel.FIVE_DAYS_WEATHER_MODEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_description, null);

        mDate = (TextView) view.findViewById(R.id.description_date);
        mImage = (ImageView) view.findViewById(R.id.description_image);
        mTemp = (TextView) view.findViewById(R.id.description_temp);
        mDesc = (TextView) view.findViewById(R.id.description);
        mClouds = (TextView) view.findViewById(R.id.clouds);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        final WeatherListModel model = mList.get(mPageNumber);

        mDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String pageDate = mDateFormat.format(new Date(model.getDate() * 1000L));
        String pageTemp = Integer.toString((int) model.getTemperature() - 273);
        String pageClouds = Integer.toString(model.getClouds());

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(llm);
        DescriptionRecyclerAdapter  adapter = new DescriptionRecyclerAdapter(divideFiveDaysListModel(WeatherListModel.getDetailedFiveDaysModel()));
        mRecyclerView.setAdapter(adapter);

        mDate.setText(pageDate);
        mTemp.setText(pageTemp);
        mClouds.setText("Облачность " + pageClouds + "%");

        switch (model.getDescription()){
            case WeatherApiManager.WEATHER_TYPE_RAIN:
                mImage.setImageResource(R.drawable.rain);
                mDesc.setText("Дождь");
                break;
            case WeatherApiManager.WEATHER_TYPE_CLEAR:
                mImage.setImageResource(R.drawable.sun);
                mDesc.setText("Ясно");
                break;
            case WeatherApiManager.WEATHER_TYPE_CLOUDS:
                mImage.setImageResource(R.drawable.cloudy);
                mDesc.setText("Облачно");
                break;
        }

        return view;
    }

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

}
