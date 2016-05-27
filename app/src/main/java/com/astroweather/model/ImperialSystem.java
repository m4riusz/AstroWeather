package com.astroweather.model;

import android.os.Parcel;

/**
 * Created by mariusz on 26.05.16.
 */
public class ImperialSystem extends MeasureSystem {

    public static final String MILES_PER_HOUR = "miles/hour";
    public static final String FAHRENHEIT_SYMBOL = "F";
    public static final String IMPERIAL = "imperial";

    public static final Creator<ImperialSystem> CREATOR = new Creator<ImperialSystem>() {
        @Override
        public ImperialSystem createFromParcel(Parcel in) {
            return new ImperialSystem();
        }

        @Override
        public ImperialSystem[] newArray(int size) {
            return new ImperialSystem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
