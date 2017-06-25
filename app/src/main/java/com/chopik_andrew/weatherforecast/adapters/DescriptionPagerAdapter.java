package com.chopik_andrew.weatherforecast.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chopik_andrew.weatherforecast.fragments.DescriptionFragment;
import com.chopik_andrew.weatherforecast.fragments.MainListFragment;

/**
 * Created by Andrew on 18.06.2017.
 */

public class DescriptionPagerAdapter extends FragmentPagerAdapter {

    static final int PAGE_COUNT_SIXTEEN = 16;
    static final int PAGE_COUNT_FIVE = 5;

    private int mMainPageNumber;

    public DescriptionPagerAdapter(FragmentManager fm, int mainPageNumber) {
        super(fm);
        mMainPageNumber = mainPageNumber;
    }

    @Override
    public Fragment getItem(int position) {
        return DescriptionFragment.newInstance(position, mMainPageNumber);
    }

    @Override
    public int getCount() {
        /*switch (mMainPageNumber){
            case 2:
                return PAGE_COUNT_FIVE;
            case 3:
                return PAGE_COUNT_SIXTEEN;
            default:
                return PAGE_COUNT_FIVE;
        }*/
        if(mMainPageNumber == MainListFragment.SIXTEEN_WEATHER_VIEW_TYPE){
            return PAGE_COUNT_SIXTEEN;
        } else return PAGE_COUNT_FIVE;
    }
}
