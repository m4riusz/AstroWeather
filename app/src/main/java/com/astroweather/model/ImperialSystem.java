package com.astroweather.model;

/**
 * Created by mariusz on 26.05.16.
 */
public class ImperialSystem extends MeasureSystem {

    public static final String MILES_PER_HOUR = "miles/hour";
    public static final String FAHRENHEIT_SYMBOL = "F";
    public static final String IMPERIAL = "imperial";

    @Override
    public String getName() {
        return IMPERIAL;
    }

    @Override
    public String getTemperatureUnits() {
        return MeasureSystem.DEGREE + FAHRENHEIT_SYMBOL;
    }

    @Override
    public String getWindSpeedUnits() {
        return MILES_PER_HOUR;
    }
}
