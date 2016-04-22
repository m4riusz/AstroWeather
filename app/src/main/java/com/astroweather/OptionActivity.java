package com.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.astroweather.util.AstroWeather;

public class OptionActivity extends AppCompatActivity {

    private EditText longitudeValue;
    private EditText latitudeValue;
    private EditText refreshRateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        initializeParams();
    }

    private void initializeParams() {
        double longitude = getIntent().getDoubleExtra(AstroWeather.LONGITUDE, AstroWeather.DEFAULT_LONGITUDE);
        double latitude = getIntent().getDoubleExtra(AstroWeather.LATITUDE, AstroWeather.DEFAULT_LATITUDE);
        int refreshRate = getIntent().getIntExtra(AstroWeather.REFRESH_RATE_TEXT, AstroWeather.DEFAULT_REFRESH_RATE);

        longitudeValue = (EditText) findViewById(R.id.longitudeValue);
        latitudeValue = (EditText) findViewById(R.id.latitudeValue);
        refreshRateValue = (EditText) findViewById(R.id.refreshRateValue);

        longitudeValue.setText("" + longitude);
        latitudeValue.setText("" + latitude);
        refreshRateValue.setText("" + refreshRate);
    }

    public void saveAndExit(View view) {
        Intent intentResult = new Intent();
        intentResult.putExtra(AstroWeather.LONGITUDE, Double.valueOf(longitudeValue.getText().toString()));
        intentResult.putExtra(AstroWeather.LATITUDE, Double.valueOf(latitudeValue.getText().toString()));
        intentResult.putExtra(AstroWeather.REFRESH_RATE_TEXT, Integer.valueOf(refreshRateValue.getText().toString()));
        setResult(AstroWeather.REQUEST_CODE, intentResult);
        finish();
    }
}
