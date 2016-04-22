package com.astroweather.util;

import com.astrocalculator.AstroCalculator;

/**
 * Created by mariusz on 16.04.16.
 */
public class AstroWeather {

    public static final int NUMBER_OF_TABS = 2;
    public static double DEFAULT_LONGITUDE = 1.1;
    public static double DEFAULT_LATITUDE = 2.2;
    public static AstroCalculator.Location location = new AstroCalculator.Location(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    public static int DEFAULT_REFRESH_RATE = 1;
    public static int REFRESH_RATE = DEFAULT_REFRESH_RATE;
    public static int REQUEST_CODE = 1;
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String REFRESH_RATE_TEXT = "refreshRate";
    public static final String TIME_FORMAT = "dd MMMM yyyy HH:mm:ss";
}
