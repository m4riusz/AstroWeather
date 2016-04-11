package com.astroweather;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astroweather.fragments.MoonFragment;
import com.astroweather.fragments.SunFragment;
import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity {

    public static final int NUMBER_OF_TABS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        if (pager != null) {
            pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), NUMBER_OF_TABS));
            PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
            tabs.setViewPager(pager);
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.sun_fragment, new SunFragment());
            fragmentTransaction.add(R.id.moon_fragment, new MoonFragment());
            fragmentTransaction.commit();
        }


    }
}
