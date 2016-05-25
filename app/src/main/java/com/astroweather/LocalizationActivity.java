package com.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.astroweather.adapters.LocalizationAdapter;
import com.astroweather.model.Localization;
import com.astroweather.model.Weather;
import com.astroweather.util.AstroWeather;

import java.util.ArrayList;

/**
 * Created by mariusz on 20.05.16.
 */
public class LocalizationActivity extends AppCompatActivity {

    private ArrayList<Localization> favouriteLocalizations = new ArrayList<>();
    private Spinner spinner;
    private LocalizationAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localization_activity);
        initialize();
    }

    private void initialize() {
        favouriteLocalizations = getIntent().getParcelableArrayListExtra(AstroWeather.FAVOURITE_LOCALIZATIONS);
        spinner = (Spinner) findViewById(R.id.favouriteLocalizationSpinner);
        adapter = new LocalizationAdapter(LocalizationActivity.this, android.R.layout.simple_spinner_item, favouriteLocalizations);
        spinner.setAdapter(adapter);
    }

    public void addNewLocalization(View view) {
        try {
            String localizationName = String.valueOf(((EditText) findViewById(R.id.localizationNameValue)).getText());
            double longitudeVal = Double.parseDouble(((EditText) findViewById(R.id.localizationLongitudeValue)).getText().toString());
            double latitudeVal = Double.parseDouble(((EditText) findViewById(R.id.localizationLatitudeValue)).getText().toString());
            //TODO add localization checker and weather list
            favouriteLocalizations.add(new Localization(localizationName, longitudeVal, latitudeVal, new ArrayList<Weather>()));
            adapter.notifyDataSetChanged();
            Toast.makeText(LocalizationActivity.this, R.string.add_localization_success, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(LocalizationActivity.this, R.string.add_localization_fail, Toast.LENGTH_SHORT).show();
        }
    }

    public void saveAndExit(View view) {
        Intent intentResult = new Intent();
        intentResult.putParcelableArrayListExtra(AstroWeather.FAVOURITE_LOCALIZATIONS, favouriteLocalizations);
        setResult(AstroWeather.LOCALIZATION_REQUEST_CODE, intentResult);
        finish();
    }

    public void deleteLocalization(View view) {
        if (favouriteLocalizations.size() > 0) {
            favouriteLocalizations.remove(spinner.getSelectedItem());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(AstroWeather.FAVOURITE_LOCALIZATIONS, favouriteLocalizations);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        favouriteLocalizations = savedInstanceState.getParcelableArrayList(AstroWeather.FAVOURITE_LOCALIZATIONS);
    }
}
