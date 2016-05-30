package com.astroweather.util;

import com.astroweather.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Json {
    public static final String URL_BY_POSITION = "http://api.openweathermap.org/data/2.5/forecast?lat={0}&lon={1}&appid={2}&units={3}";
    public static final String URL_BY_NAME = "http://api.openweathermap.org/data/2.5/forecast?q={0}&appid={1}&units={2}";
    public static final String GET_METHOD = "GET";
    public static final String PNG = ".png";
    public static final String IMAGE_URL = "http://openweathermap.org/img/w/";
    public static final String WEATHER_LIST = "list";
    public static final String JSON_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_STRING = "dt_txt";
    public static final String BASIC = "main";
    public static final String TEMPERATURE = "temp";
    public static final String HUMIDITY = "humidity";
    public static final String PRESSURE = "pressure";
    public static final String SPEED = "speed";
    public static final String DIRECTION = "deg";
    public static final String WIND = "wind";
    public static final String CLOUDS = "clouds";
    public static final String PERCENTAGE = "all";
    public static final String WEATHER = "weather";
    public static final String ICON = "icon";
    public static final String CITY = "city";
    public static final String COORD = "coord";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lon";


    public static Weather parseWeather(SimpleDateFormat simpleDateFormat, JSONObject weatherJSON) throws JSONException, ParseException {
        JSONObject cloudsInfo = weatherJSON.getJSONObject(CLOUDS);
        JSONObject basicInfo = weatherJSON.getJSONObject(BASIC);
        JSONObject windInfo = weatherJSON.getJSONObject(WIND);
        JSONArray weatherInfo = weatherJSON.getJSONArray(WEATHER);
        Date date = simpleDateFormat.parse(weatherJSON.getString(DATE_STRING));
        float temperature = (float) basicInfo.getDouble(TEMPERATURE);
        float humidity = (float) basicInfo.getDouble(HUMIDITY);
        float pressure = (float) basicInfo.getDouble(PRESSURE);
        float windSpeed = (float) windInfo.getDouble(SPEED);
        float windDirection = (float) windInfo.getDouble(DIRECTION);
        float clouds = cloudsInfo.getInt(PERCENTAGE);
        String iconCOde = ((JSONObject) weatherInfo.get(0)).getString(ICON) + PNG;
        return new Weather(date, temperature, humidity, pressure, windSpeed, windDirection, clouds, iconCOde);
    }

    public static JSONObject getJSON(String url) throws IOException, JSONException {
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
}