package com.astroweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mariusz on 24.05.16.
 */
public class Weather implements Parcelable {
    private Date date;
    private int temperature;
    private float humidity;
    private float pressure;
    private float windSpeed;
    private float windDirection;
    private float clouds;

    public Weather(Date date, int temperature, float humidity, float pressure, float windSpeed, float windDirection, float clouds) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.clouds = clouds;
    }

    protected Weather(Parcel in) {
        date = (Date) in.readSerializable();
        temperature = in.readInt();
        humidity = in.readFloat();
        pressure = in.readFloat();
        windSpeed = in.readFloat();
        windDirection = in.readFloat();
        clouds = in.readFloat();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(date);
        parcel.writeInt(temperature);
        parcel.writeFloat(humidity);
        parcel.writeFloat(pressure);
        parcel.writeFloat(windSpeed);
        parcel.writeFloat(windDirection);
        parcel.writeFloat(clouds);
    }
}
