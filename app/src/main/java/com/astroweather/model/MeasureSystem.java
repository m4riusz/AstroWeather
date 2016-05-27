package com.astroweather.model;

import android.os.Parcelable;

/**
 * Created by mariusz on 26.05.16.
 */
public abstract class MeasureSystem implements Parcelable {

    public static final String PERCENT = "%";
    public static final String H_PA = "hPa";
    public static final String DEGREE = "°";

    public abstract String getName();

    public abstract String getTemperatureUnits();

    public abstract String getWindSpeedUnits();

    public String getHumidityUnits() {
        return PERCENT;
    }

    public String getPressureUnits() {
        return H_PA;
    }

    public String getCloudUnits() {
        return PERCENT;
    }

    public String getWindDirectionUnits() {
        return DEGREE;
    }
}
