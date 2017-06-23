package com.chopik_andrew.weatherforecast.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chopik_andrew.weatherforecast.fragments.DescriptionFragment;

/**
 * Created by Andrew on 18.06.2017.
 */

public class DescriptionPagerAdapter extends FragmentPagerAdapter {

    static final int PAGE_COUNT = 5;

    public DescriptionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DescriptionFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
