package com.astroweather;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astroweather.fragments.MoonFragment;
import com.astroweather.fragments.SunFragment;
import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity {

    public static final int NUMBER_OF_TABS = 2;
    private double latitude;
    private double longitude;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_localization:
                Log.i("WTF", "localization selected: ");
                return true;
            case R.id.menu_delay:
                Log.i("WTF", "delay selected");
                return true;
        }
        return false;
    }
}
