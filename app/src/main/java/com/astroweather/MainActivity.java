package com.astroweather;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astroweather.fragments.MoonFragment;
import com.astroweather.fragments.SunFragment;
import com.astroweather.util.AstroWeather;
import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity {

    public static final int NUMBER_OF_TABS = 2;
    private double latitude;
    private double longitude;
    private int refreshRate;

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
            case R.id.menu_settings:
                Intent intent = new Intent(this, OptionActivity.class);
                intent.putExtra(AstroWeather.LONGITUDE, longitude);
                intent.putExtra(AstroWeather.LATITUDE, latitude);
                intent.putExtra(AstroWeather.REFRESH_RATE, refreshRate);
                startActivityForResult(intent, AstroWeather.REQUEST_CODE);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == AstroWeather.REQUEST_CODE) {
            latitude = data.getDoubleExtra(AstroWeather.LATITUDE, AstroWeather.DEFAULT_LATITUDE);
            longitude = data.getDoubleExtra(AstroWeather.LONGITUDE, AstroWeather.DEFAULT_LONGITUDE);
            refreshRate = data.getIntExtra(AstroWeather.REFRESH_RATE, AstroWeather.DEFAULT_REFRESH_RATE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(AstroWeather.LATITUDE, latitude);
        outState.putDouble(AstroWeather.LONGITUDE, longitude);
        outState.putInt(AstroWeather.REFRESH_RATE, refreshRate);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        latitude = savedInstanceState.getDouble(AstroWeather.LATITUDE);
        longitude = savedInstanceState.getDouble(AstroWeather.LONGITUDE);
        refreshRate = savedInstanceState.getInt(AstroWeather.REFRESH_RATE);
    }
}
