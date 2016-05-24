package com.astroweather;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.astroweather.model.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mariusz on 24.05.16.
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {
    private final Context context;
    private final List<Weather> weatherList;

    public WeatherAdapter(Context context, int resource, List<Weather> weatherList) {
        super(context, resource, weatherList);
        this.context = context;
        this.weatherList = weatherList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        prepareLabel(position, label);
        return label;
    }

    private void prepareLabel(int position, TextView label) {
        DateFormat df = new SimpleDateFormat("dd:MM:yyyy");
        String name = df.format(weatherList.get(position).getDate());
        label.setText(formatToText(name));
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        prepareLabel(position, label);
        return label;
    }

    private String formatToText(String name) {
        return String.format("Dzień %s", name);
    }
}
