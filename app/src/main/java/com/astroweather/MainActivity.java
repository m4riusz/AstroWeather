package com.astroweather;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.astroweather.adapters.FragmentAdapter;
import com.astroweather.fragments.MoonFragment;
import com.astroweather.fragments.SunFragment;
import com.astroweather.fragments.WeatherFragment;
import com.astroweather.model.Localization;
import com.astroweather.util.AstroWeather;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private double latitude = AstroWeather.DEFAULT_LATITUDE;
    private double longitude = AstroWeather.DEFAULT_LONGITUDE;
    private int refreshRate = AstroWeather.DEFAULT_REFRESH_RATE;
    private GregorianCalendar calendar = new GregorianCalendar();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(AstroWeather.TIME_FORMAT);
    private TextView textView;
    private ArrayList<Localization> favouriteLocalizations;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            initTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFirstTimeStarted(savedInstanceState)) {
            initializeLocalizations();
        }
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.timeValue);
        runnable.run();
        determineScreenType();
    }

    private void initializeLocalizations() {
        favouriteLocalizations = (ArrayList<Localization>) loadFromFile();
        AstroWeather.localizationList = favouriteLocalizations;
        if (AstroWeather.isOnline(this)) {
            Toast.makeText(this, R.string.update_data_downloading, Toast.LENGTH_LONG).show();
            new UpdateLocalizationTask(AstroWeather.localizationList, favouriteLocalizations).execute(favouriteLocalizations);
            saveToFile(favouriteLocalizations);
            return;
        }
        Toast.makeText(this, R.string.update_data_no_internet, Toast.LENGTH_LONG).show();
    }

    private boolean isFirstTimeStarted(Bundle savedInstanceState) {
        return savedInstanceState == null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.removeCallbacks(runnable);
        initTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
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
                intent.putExtra(AstroWeather.REFRESH_RATE_TEXT, refreshRate);
                startActivityForResult(intent, AstroWeather.SETTINGS_REQUEST_CODE);
                return true;
            case R.id.menu_localizations:
                Intent localizationIntent = new Intent(this, LocalizationActivity.class);
                localizationIntent.putParcelableArrayListExtra(AstroWeather.FAVOURITE_LOCALIZATIONS, favouriteLocalizations);
                startActivityForResult(localizationIntent, AstroWeather.LOCALIZATION_REQUEST_CODE);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                case AstroWeather.SETTINGS_REQUEST_CODE:
                    latitude = data.getDoubleExtra(AstroWeather.LATITUDE, AstroWeather.DEFAULT_LATITUDE);
                    longitude = data.getDoubleExtra(AstroWeather.LONGITUDE, AstroWeather.DEFAULT_LONGITUDE);
                    refreshRate = data.getIntExtra(AstroWeather.REFRESH_RATE_TEXT, AstroWeather.DEFAULT_REFRESH_RATE);
                    AstroWeather.REFRESH_RATE = refreshRate;
                    AstroWeather.location.setLatitude(latitude);
                    AstroWeather.location.setLongitude(longitude);
                    break;
                case AstroWeather.LOCALIZATION_REQUEST_CODE:
                    favouriteLocalizations = data.getParcelableArrayListExtra(AstroWeather.FAVOURITE_LOCALIZATIONS);
                    AstroWeather.localizationList = favouriteLocalizations;
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(AstroWeather.LATITUDE, latitude);
        outState.putDouble(AstroWeather.LONGITUDE, longitude);
        outState.putInt(AstroWeather.REFRESH_RATE_TEXT, refreshRate);
        outState.putParcelableArrayList(AstroWeather.FAVOURITE_LOCALIZATIONS, favouriteLocalizations);
        outState.remove("android:support:fragments");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        latitude = savedInstanceState.getDouble(AstroWeather.LATITUDE);
        longitude = savedInstanceState.getDouble(AstroWeather.LONGITUDE);
        refreshRate = savedInstanceState.getInt(AstroWeather.REFRESH_RATE_TEXT);
        favouriteLocalizations = savedInstanceState.getParcelableArrayList(AstroWeather.FAVOURITE_LOCALIZATIONS);
    }

    private void initTime() {
        textView.setText(dateFormatter.format(calendar.getTime()));
        calendar.add(Calendar.SECOND, 1);
        handler.postDelayed(runnable, 1000);
    }

    private void determineScreenType() {
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        if (pager != null) {
            pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), AstroWeather.NUMBER_OF_TABS));
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.sun_fragment, new SunFragment());
            fragmentTransaction.replace(R.id.moon_fragment, new MoonFragment());
            fragmentTransaction.replace(R.id.weather_fragment, new WeatherFragment());
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveToFile(AstroWeather.localizationList);
    }

    private void saveToFile(List<Localization> localizationList) {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(getCacheDir() + File.separator + AstroWeather.LOCALIZATION_FILE_NAME);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(localizationList);
            objectOutputStream.close();
        } catch (IOException e) {
            Log.e(AstroWeather.APP_TAG, e.toString());
            e.printStackTrace();
        }
    }

    private List<Localization> loadFromFile() {
        FileInputStream fileInputStream = null;
        try {
            File file = new File(getCacheDir() + File.separator + AstroWeather.LOCALIZATION_FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Localization> localizationList = (List<Localization>) objectInputStream.readObject();
            objectInputStream.close();
            return localizationList;
        } catch (ClassNotFoundException | IOException e) {
            Log.e(AstroWeather.APP_TAG, e.toString());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
