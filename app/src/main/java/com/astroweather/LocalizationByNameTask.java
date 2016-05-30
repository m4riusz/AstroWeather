package com.astroweather;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.astroweather.model.Localization;
import com.astroweather.model.MeasureSystem;
import com.astroweather.model.Weather;
import com.astroweather.util.AstroWeather;
import com.astroweather.util.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariusz on 25.05.16.
 */
public class LocalizationByNameTask extends AsyncTask<String, Void, Void> {

    private Activity activity;
    private List<Localization> localizations;
    private ArrayAdapter adapter;
    private String localizationName;
    private MeasureSystem measureSystem;

    public LocalizationByNameTask(Activity activity, String localizationName, List<Localization> localizationList, ArrayAdapter adapter, MeasureSystem measureSystem) {
        this.activity = activity;
        this.localizationName = localizationName;
        this.localizations = localizationList;
        this.adapter = adapter;
        this.measureSystem = measureSystem;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(activity.getApplicationContext(), R.string.add_localization_wait, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(String... cityNames) {

        for (String city : cityNames) {
            String url = MessageFormat.format(Json.URL_BY_NAME, city, AstroWeather.apiKey, measureSystem.getName());
            try {
                Localization localization = new Localization(localizationName, 0, 0, new ArrayList<Weather>(), measureSystem);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Json.JSON_DATE_FORMAT);
                JSONObject response = Json.getJSON(url);
                setLocalizationPosition(localization, response);
                JSONArray weathers = response.getJSONArray(Json.WEATHER_LIST);
                for (int weatherIndex = 0; weatherIndex < weathers.length(); weatherIndex++) {
                    Weather weather = Json.parseWeather(simpleDateFormat, weathers.getJSONObject(weatherIndex));
                    localization.getWeathers().add(weather);
                }
                localizations.add(localization);
            } catch (ParseException | IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void setLocalizationPosition(Localization localization, JSONObject response) throws JSONException {
        JSONObject cityObject = response.getJSONObject(Json.CITY);
        JSONObject coord = cityObject.getJSONObject(Json.COORD);
        double latitude = coord.getDouble(Json.LATITUDE);
        double longitude = coord.getDouble(Json.LONGITUDE);
        localization.setLatitude(latitude);
        localization.setLongitude(longitude);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(activity, R.string.add_localization_success, Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
}
