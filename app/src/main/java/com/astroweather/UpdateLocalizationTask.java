package com.astroweather;

import android.os.AsyncTask;

import com.astroweather.model.Localization;
import com.astroweather.model.Weather;
import com.astroweather.util.AstroWeather;
import com.astroweather.util.Json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
            String url = MessageFormat.format(Json.URL, localization.getLatitude(), localization.getLongitude(), AstroWeather.apiKey, localization.getMeasureSystem().getName());
            try {
                JSONObject response = Json.getJSON(url);
                JSONArray weathers = response.getJSONArray(Json.WEATHER_LIST);
                for (int weatherIndex = 0; weatherIndex < weathers.length(); weatherIndex++) {
                    Weather weather = Json.parseWeather(simpleDateFormat, weathers.getJSONObject(weatherIndex));
                    localization.getWeathers().add(weather);
                }
                tmpList.add(localization);
            } catch (Exception ex) {
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

}
