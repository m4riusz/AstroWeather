package com.astroweather.util;

import com.astrocalculator.AstroCalculator;
import com.astroweather.model.Localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariusz on 16.04.16.
 */
public class AstroWeather {
    public static int ZONE_OFFSET_DIVISOR = 60 * 60 * 1000;
    public static final int NUMBER_OF_TABS =3;
    public static double DEFAULT_LONGITUDE = 1.1;
    public static double DEFAULT_LATITUDE = 2.2;
    public static AstroCalculator.Location location = new AstroCalculator.Location(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    public static int DEFAULT_REFRESH_RATE = 1;
    public static int REFRESH_RATE = DEFAULT_REFRESH_RATE;
    public static final int SETTINGS_REQUEST_CODE = 1;
    public static final int LOCALIZATION_REQUEST_CODE = 2;
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String REFRESH_RATE_TEXT = "refreshRate";
    public static final String TIME_FORMAT = "dd MMMM yyyy HH:mm:ss";
    public static final String FAVOURITE_LOCALIZATIONS = "favouriteLocalizations";
    public static List<Localization> localizationList = new ArrayList<>();
}
