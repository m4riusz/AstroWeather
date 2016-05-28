package com.astroweather.model;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by mariusz on 26.05.16.
 */
public class MetricSystem extends MeasureSystem implements Serializable{

    public static final String METERS_PER_SECOND = "meters/sec";
    public static final String CELSIUS_SYMBOL = "C";
    public static final String METRIC = "metric";

    public static final Creator<MetricSystem> CREATOR = new Creator<MetricSystem>() {
        @Override
        public MetricSystem createFromParcel(Parcel in) {
            return new MetricSystem();
        }

        @Override
        public MetricSystem[] newArray(int size) {
            return new MetricSystem[size];
        }
    };

    @Override
    public String getName() {
        return METRIC;
    }

    @Override
    public String getTemperatureUnits() {
        return MeasureSystem.DEGREE + CELSIUS_SYMBOL;
    }

    @Override
    public String getWindSpeedUnits() {
        return METERS_PER_SECOND;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
