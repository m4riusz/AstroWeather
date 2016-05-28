package com.astroweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astroweather.ImageTask;
import com.astroweather.LocalizationTask;
import com.astroweather.R;
import com.astroweather.adapters.LocalizationAdapter;
import com.astroweather.adapters.WeatherAdapter;
import com.astroweather.model.Localization;
import com.astroweather.model.MeasureSystem;
import com.astroweather.model.Weather;
import com.astroweather.util.AstroWeather;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mariusz on 18.05.16.
 */
public class WeatherFragment extends Fragment implements View.OnClickListener {

    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView pressureTextView;
    private TextView windSpeedTextView;
    private TextView windDirectionTextView;
    private TextView cloudsTextView;
    private ImageView imageView;
    private LocalizationAdapter localizationAdapter;
    private Spinner localizationSpinner;
    private Spinner dateSpinner;
    private WeatherAdapter dateSpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.weather_fragment_layout, container, false);
        Button upButton = (Button) inflate.findViewById(R.id.updateWeatherButton);
        upButton.setOnClickListener(this);
        initTextViews();
        initSpinnersAndAdapters(inflate);
        return inflate;
    }

    private void initSpinnersAndAdapters(View inflate) {
        dateSpinner = (Spinner) inflate.findViewById(R.id.daySpinner);
        dateSpinnerAdapter = new WeatherAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList<Weather>());
        dateSpinner.setAdapter(dateSpinnerAdapter);
        setListenerOnDateSpinner();

        localizationSpinner = (Spinner) inflate.findViewById(R.id.localizationSpinner);
        localizationAdapter = new LocalizationAdapter(getContext(), android.R.layout.simple_spinner_item, AstroWeather.localizationList);
        localizationSpinner.setAdapter(localizationAdapter);
        setListenerOnLocalizationSpinner();
    }

    private Localization getSelectedLocalization() {
        return ((Localization) localizationSpinner.getSelectedItem());
    }

    private Weather getSelectedWeather() {
        return ((Weather) dateSpinner.getSelectedItem());
    }

    public void updateTextViews(Weather weather, Localization localization) {
        MeasureSystem measureSystem = localization.getMeasureSystem();
        temperatureTextView.setText(String.format("%s %s", weather.getTemperature(), measureSystem.getTemperatureUnits()));
        humidityTextView.setText(String.format("%s %s", weather.getHumidity(), measureSystem.getHumidityUnits()));
        pressureTextView.setText(String.format("%s %s", weather.getPressure(), measureSystem.getPressureUnits()));
        windSpeedTextView.setText(String.format("%s %s", weather.getWindSpeed(), measureSystem.getWindSpeedUnits()));
        windDirectionTextView.setText(String.format("%s %s", weather.getWindDirection(), measureSystem.getWindDirectionUnits()));
        cloudsTextView.setText(String.format("%s %s", weather.getClouds(), measureSystem.getCloudUnits()));
    }

    private void initTextViews() {
        temperatureTextView = (TextView) getActivity().findViewById(R.id.temperatureValue);
        humidityTextView = (TextView) getActivity().findViewById(R.id.humidityValue);
        pressureTextView = (TextView) getActivity().findViewById(R.id.preasureValue);
        windSpeedTextView = (TextView) getActivity().findViewById(R.id.windSpeedValue);
        windDirectionTextView = (TextView) getActivity().findViewById(R.id.windDirectionValue);
        cloudsTextView = (TextView) getActivity().findViewById(R.id.cloudsValue);
        imageView = (ImageView) getActivity().findViewById(R.id.weatherIcon);
    }

    private boolean isTextViewInitialized() {
        return (temperatureTextView != null && humidityTextView != null && pressureTextView != null && windSpeedTextView != null && windDirectionTextView != null && cloudsTextView != null && imageView != null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateWeatherButton:
                if (getSelectedLocalization() != null && getSelectedWeather() != null) {
                    Localization selectedLocalization = getSelectedLocalization();
                    if (AstroWeather.isOnline(getContext())) {
                        AstroWeather.localizationList.remove(selectedLocalization);
                        new LocalizationTask(getActivity(), selectedLocalization.getName(), AstroWeather.localizationList,
                                localizationAdapter, selectedLocalization.getMeasureSystem())
                                .execute(selectedLocalization.getLongitude(), selectedLocalization.getLatitude());
                    } else {
                        Toast.makeText(getContext(), R.string.add_localization_no_internet_connection, Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        localizationAdapter = new LocalizationAdapter(getContext(), android.R.layout.simple_spinner_item, AstroWeather.localizationList);
        localizationSpinner.setAdapter(localizationAdapter);

        if (!isTextViewInitialized()) {
            initTextViews();
        }
    }

    private void setListenerOnLocalizationSpinner() {
        localizationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Weather> weatherList = localizationAdapter.getItem(i).getWeathers();
                for (int j = 0; j < weatherList.size() - 1; j++) {
                    dateSpinnerAdapter = new WeatherAdapter(getContext(), android.R.layout.simple_spinner_item, weatherList);
                    dateSpinner.setAdapter(dateSpinnerAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setListenerOnDateSpinner() {
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Weather weather = dateSpinnerAdapter.getItem(i);
                new ImageTask(imageView).execute(weather.getIconCode());
                updateTextViews(weather, getSelectedLocalization());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
