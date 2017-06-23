package com.chopik_andrew.weatherforecast.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chopik_andrew.weatherforecast.fragments.DescriptionFragment;
import com.chopik_andrew.weatherforecast.fragments.MainListFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;

    public static final int NOW_POSITION = 0;
    public static final int YESTERDAY_POSITION = 1;
    public static final int OTHER_POSITION = 2;

    private FragmentManager mFragmentManager;


    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case NOW_POSITION:
                return DescriptionFragment.newInstance(NOW_POSITION);

            case YESTERDAY_POSITION:

                return MainListFragment.newInstance(MainListFragment.FIVE_WEATHER_VIEW_TYPE);

            case OTHER_POSITION:

                return MainListFragment.newInstance(MainListFragment.SIXTEEN_WEATHER_VIEW_TYPE);

            default:
                throw new IllegalArgumentException("Bad Fragment type exception");
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case NOW_POSITION:
                return "Today";

            case YESTERDAY_POSITION:
                return "5 Days";

            case OTHER_POSITION:
                return "16 Days";

            default:
                return super.getPageTitle(position);
        }
    }
}
