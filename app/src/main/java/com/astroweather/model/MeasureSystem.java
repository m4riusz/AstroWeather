package com.astroweather.model;

/**
 * Created by mariusz on 26.05.16.
 */
public abstract class MeasureSystem {

    public static final String PERCENT = "%";
    public static final String H_PA = "hPa";
    public static final String DEGREE = "°";

    public abstract String getTemperatureUnits();

    public abstract String getWindSpeedUnits();

    public String getHumidityUnits() {
        return PERCENT;
    }

    public String getPreasureUnits() {
        return H_PA;
    }

    public String getCloudUnits() {
        return PERCENT;
    }

    public String getWindDirectionUnits() {
        return DEGREE;
    }
}
