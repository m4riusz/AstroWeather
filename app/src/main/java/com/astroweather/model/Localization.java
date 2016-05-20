package com.astroweather.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mariusz on 20.05.16.
 */
public class Localization implements Parcelable {


    private String name;
    private double longitude;
    private double latitude;

    public Localization(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected Localization(Parcel in) {
        name = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
    }

    public static final Creator<Localization> CREATOR = new Creator<Localization>() {
        @Override
        public Localization createFromParcel(Parcel in) {
            return new Localization(in);
        }

        @Override
        public Localization[] newArray(int size) {
            return new Localization[size];
        }
    };
}
