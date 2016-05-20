package com.astroweather;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.astroweather.model.Localization;

import java.util.List;

/**
 * Created by mariusz on 20.05.16.
 */
public class LocalizationAdapter extends ArrayAdapter<Localization> {

    private final Context context;
    private final List<Localization> localizations;

    public LocalizationAdapter(Context context, int resource, List<Localization> localizations) {
        super(context, resource, localizations);
        this.context = context;
        this.localizations = localizations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        String name = localizations.get(position).getName();
        double longitude = localizations.get(position).getLongitude();
        double latitude = localizations.get(position).getLatitude();
        label.setText(formatToText(name, longitude, latitude));
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        String name = localizations.get(position).getName();
        double longitude = localizations.get(position).getLongitude();
        double latitude = localizations.get(position).getLatitude();
        label.setText(formatToText(name, longitude, latitude));
        return label;
    }

    private String formatToText(String name, double longitude, double latitude) {
        return String.format("%s (Długość: %s, Szerokość: %s )", name, longitude, latitude);
    }
}
