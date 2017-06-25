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
import com.chopik_andrew.weatherforecast.activity.WeatherDescriptionActivity;
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
        if (mMainPageNumber == MainListFragment.SIXTEEN_WEATHER_VIEW_TYPE){
            mList = WeatherListModel.getWeatherList(WeatherListModel.SIXTEEN_DAYS_WEATHER_MODEL);
        } else{
            mList = WeatherListModel.getWeatherList(WeatherListModel.FIVE_DAYS_WEATHER_MODEL);
        }
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

        final WeatherListModel model = mList.get(mPageNumber);

        mDateFormat = new SimpleDateFormat("E");
        String pageDate = mDateFormat.format(new Date(model.getDate() * 1000L));
        String pageTemp = Integer.toString((int) model.getTemperature() - 273);
        String pageClouds = Integer.toString(model.getClouds());
        String pageTempNight = Integer.toString((int) model.getTemperatureNight() - 273);
        String pagePressure = Double.toString(model.getPressure());
        String pageHumidity = Integer.toString(model.getHumidity());
        String pageSpeed = Double.toString(model.getSpeed());

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(llm);
        DescriptionRecyclerAdapter  adapter = new DescriptionRecyclerAdapter(divideFiveDaysListModel(WeatherListModel.getDetailedFiveDaysModel()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.hasFixedSize();

        mTemperature.setText(pageTemp);
        mTemperatureNight.setText(pageTempNight);
        mClouds.setText("Облачность: " + pageClouds + "%");
        mSpeed.setText("Скорость ветра: " + pageSpeed + "м/c");
        mHumidity.setText("Влажность: " + pageHumidity + "%");
        mPressure.setText("Давление: " + pagePressure + "гПа");

        switch (model.getDescription()){
            case WeatherApiManager.WEATHER_TYPE_RAIN:
                mImage.setImageResource(R.drawable.rain_huge);
                mDescription.setText("Дождь");
                break;
            case WeatherApiManager.WEATHER_TYPE_CLEAR:
                mImage.setImageResource(R.drawable.sun_huge);
                mDescription.setText("Ясно");
                break;
            case WeatherApiManager.WEATHER_TYPE_CLOUDS:
                mImage.setImageResource(R.drawable.cloudy_huge);
                mDescription.setText("Облачно");
                break;
        }

        switch (pageDate){
            case "пн":
                mDate.setText("Понедельник");
                break;
            case "вт":
                mDate.setText("Вторник");
                break;
            case "ср":
                mDate.setText("Среда");
                break;
            case "чт":
                mDate.setText("Четверг");
                break;
            case "пт":
                mDate.setText("Пятница");
                break;
            case "сб":
                mDate.setText("Суббота");
                break;
            case "вс":
                mDate.setText("Воскресенье");
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
