package com.astroweather;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.astroweather.model.Localization;
import com.astroweather.model.Weather;
import com.astroweather.util.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mariusz on 25.05.16.
 */
public class LocalizationTask extends AsyncTask<Double, Void, Void> {
    public static final String URL = "http://api.openweathermap.org/data/2.5/forecast?lat={0}&lon={1}&appid={2}";
    public static final String GET_METHOD = "GET";

    private static String apiKey = "602e239bf31bef4b5de4da70e251b5d0";
    private Activity activity;
    private List<Localization> localizations;
    private ArrayAdapter adapter;
    private String localizationName;

    public LocalizationTask(Activity activity, String localizationName, List<Localization> localizationList, ArrayAdapter adapter) {
        this.activity = activity;
        this.localizationName = localizationName;
        this.localizations = localizationList;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(activity.getApplicationContext(), R.string.add_localization_wait, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Double... doubles) {
        Double latitude = doubles[1];
        Double longitude = doubles[0];

        String url = MessageFormat.format(URL, latitude, longitude, apiKey);
        try {
            Localization localization = new Localization(localizationName, longitude, latitude, new ArrayList<Weather>());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Json.JSON_DATE_FORMAT);
            JSONObject response = getJSON(url);

            JSONArray weathers = response.getJSONArray(Json.WEATHER_LIST);
            for (int weatherIndex = 0; weatherIndex < weathers.length(); weatherIndex++) {
                Weather weather = parseWeather(simpleDateFormat, weathers.getJSONObject(weatherIndex));
                localization.getWeathers().add(weather);
            }
            localizations.add(localization);
        } catch (ParseException | IOException | JSONException e) {
            Toast.makeText(activity.getApplicationContext(), R.string.add_localization_fail, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    private Weather parseWeather(SimpleDateFormat simpleDateFormat, JSONObject weatherJSON) throws JSONException, ParseException {
        JSONObject cloudsInfo = weatherJSON.getJSONObject(Json.CLOUDS);
        JSONObject basicInfo = weatherJSON.getJSONObject(Json.BASIC);
        JSONObject windInfo = weatherJSON.getJSONObject(Json.WIND);
        Date date = simpleDateFormat.parse(weatherJSON.getString(Json.DATE_STRING));
        float temperature = (float) basicInfo.getDouble(Json.TEMPERATURE);
        float humidity = (float) basicInfo.getDouble(Json.HUMIDITY);
        float pressure = (float) basicInfo.getDouble(Json.PRESSURE);
        float windSpeed = (float) windInfo.getDouble(Json.SPEED);
        float windDirection = (float) windInfo.getDouble(Json.DIRECTION);
        float clouds = cloudsInfo.getInt(Json.PERCENTAGE);
        return new Weather(date, temperature, humidity, pressure, windSpeed, windDirection, clouds);
    }

    private JSONObject getJSON(String url) throws IOException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(GET_METHOD);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer content = new StringBuffer();
        String line = null;
        while ((line = buffer.readLine()) != null) {
            content.append(line);
        }
        inputStream.close();
        connection.disconnect();
        return new JSONObject(content.toString());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(activity, R.string.add_localization_success, Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
}
