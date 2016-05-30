package com.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.astroweather.adapters.LocalizationAdapter;
import com.astroweather.model.ImperialSystem;
import com.astroweather.model.Localization;
import com.astroweather.model.MeasureSystem;
import com.astroweather.model.MetricSystem;
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
        if (!AstroWeather.isOnline(this)) {
            Toast.makeText(LocalizationActivity.this, R.string.add_localization_no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String localizationName = String.valueOf(((EditText) findViewById(R.id.localizationNameValue)).getText());
            String longitudeText = ((EditText) findViewById(R.id.localizationLongitudeValue)).getText().toString();
            String latitudeText = ((EditText) findViewById(R.id.localizationLatitudeValue)).getText().toString();

            if (!localizationName.equals("") && longitudeText.equals("") && latitudeText.equals("")) {
                new LocalizationByNameTask(this, localizationName, favouriteLocalizations, adapter, getMeasureSystem()).execute(localizationName);
                return;
            }
            double longitudeVal = Double.parseDouble(longitudeText);
            double latitudeVal = Double.parseDouble(latitudeText);
            new LocalizationByPositionsTask(this, localizationName, favouriteLocalizations, adapter, getMeasureSystem()).execute(longitudeVal, latitudeVal);

        } catch (Exception ex) {
            Toast.makeText(LocalizationActivity.this, R.string.add_localization_fail, Toast.LENGTH_SHORT).show();
        }
    }

    private MeasureSystem getMeasureSystem() {
        RadioButton metricSystem = (RadioButton) findViewById(R.id.metricSystem);
        if (metricSystem.isChecked()) {
            return new MetricSystem();
        }
        return new ImperialSystem();
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
