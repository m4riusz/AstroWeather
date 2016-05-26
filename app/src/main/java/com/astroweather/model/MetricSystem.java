package com.astroweather.model;

/**
 * Created by mariusz on 26.05.16.
 */
public class MetricSystem extends MeasureSystem {

    public static final String METERS_PER_SECOND = "meters/sec";
    public static final String CELSIUS_SYMBOL = "C";
    public static final String METRIC = "metric";

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
}
