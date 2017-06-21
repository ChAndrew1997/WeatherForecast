package com.chopik_andrew.weatherforecast.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chopik_andrew.weatherforecast.fragments.MainListFragment;
import com.chopik_andrew.weatherforecast.fragments.TodayFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {

    static final int PAGE_COUNT = 3;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new TodayFragment();
        else
            return MainListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
