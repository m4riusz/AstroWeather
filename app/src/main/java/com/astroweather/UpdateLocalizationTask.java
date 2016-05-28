package com.astroweather;

import android.os.AsyncTask;
import android.util.Log;

import com.astroweather.model.Localization;
import com.astroweather.model.Weather;
import com.astroweather.util.AstroWeather;
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
 * Created by mariusz on 28.05.16.
 */
public class UpdateLocalizationTask extends AsyncTask<List<Localization>, Void, Void> {

    private List<Localization> localizationList;
    private List<Localization> localizationList2;
    private List<Localization> tmpList;

    public UpdateLocalizationTask(List<Localization> localizationList, List<Localization> localizationList2) {
        this.localizationList = localizationList;
        this.localizationList2 = localizationList2;
        this.tmpList = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(List<Localization>... localizations) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Json.JSON_DATE_FORMAT);

        for (Localization localization : localizations[0]) {
            localization.getWeathers().clear();
            String url = MessageFormat.format(LocalizationTask.URL, localization.getLatitude(), localization.getLongitude(), AstroWeather.apiKey, localization.getMeasureSystem().getName());
            try {
                JSONObject response = getJSON(url);
                JSONArray weathers = response.getJSONArray(Json.WEATHER_LIST);
                for (int weatherIndex = 0; weatherIndex < weathers.length(); weatherIndex++) {
                    Weather weather = parseWeather(simpleDateFormat, weathers.getJSONObject(weatherIndex));
                    localization.getWeathers().add(weather);
                }
                tmpList.add(localization);
            } catch (Exception ex) {
                Log.e(AstroWeather.APP_TAG, ex.getMessage());
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        localizationList = tmpList;
        localizationList2 = tmpList;
    }

    private Weather parseWeather(SimpleDateFormat simpleDateFormat, JSONObject weatherJSON) throws JSONException, ParseException {
        JSONObject cloudsInfo = weatherJSON.getJSONObject(Json.CLOUDS);
        JSONObject basicInfo = weatherJSON.getJSONObject(Json.BASIC);
        JSONObject windInfo = weatherJSON.getJSONObject(Json.WIND);
        JSONArray weatherInfo = weatherJSON.getJSONArray(Json.WEATHER);
        Date date = simpleDateFormat.parse(weatherJSON.getString(Json.DATE_STRING));
        float temperature = (float) basicInfo.getDouble(Json.TEMPERATURE);
        float humidity = (float) basicInfo.getDouble(Json.HUMIDITY);
        float pressure = (float) basicInfo.getDouble(Json.PRESSURE);
        float windSpeed = (float) windInfo.getDouble(Json.SPEED);
        float windDirection = (float) windInfo.getDouble(Json.DIRECTION);
        float clouds = cloudsInfo.getInt(Json.PERCENTAGE);
        String iconCOde = ((JSONObject) weatherInfo.get(0)).getString(Json.ICON) + LocalizationTask.PNG;
        Weather weather = new Weather(date, temperature, humidity, pressure, windSpeed, windDirection, clouds, iconCOde);
        return weather;
    }

    private JSONObject getJSON(String url) throws IOException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(LocalizationTask.GET_METHOD);
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

}
