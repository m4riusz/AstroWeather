package com.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.astroweather.fragments.MoonFragment;
import com.astroweather.fragments.SunFragment;
import com.astroweather.fragments.WeatherFragment;

/**
 * Created by mariusz on 11.04.16.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private String titles[] = {"Sun", "Moon", "Weather"};

    public FragmentAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SunFragment();
            case 1:
                return new MoonFragment();
            case 2:
                return new WeatherFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
