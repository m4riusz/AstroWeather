package com.astroweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mariusz on 24.05.16.
 */
public class Weather implements Parcelable,Serializable {
    private Date date;
    private float temperature;
    private float humidity;
    private float pressure;
    private float windSpeed;
    private float windDirection;
    private float clouds;
    private String iconCode;

    public Weather(Date date, float temperature, float humidity, float pressure, float windSpeed, float windDirection, float clouds, String iconCode) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.clouds = clouds;
        this.iconCode = iconCode;
    }


    protected Weather(Parcel in) {
        date = new Date(in.readLong());
        temperature = in.readFloat();
        humidity = in.readFloat();
        pressure = in.readFloat();
        windSpeed = in.readFloat();
        windDirection = in.readFloat();
        clouds = in.readFloat();
        iconCode = in.readString();
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

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(date.getTime());
        parcel.writeFloat(temperature);
        parcel.writeFloat(humidity);
        parcel.writeFloat(pressure);
        parcel.writeFloat(windSpeed);
        parcel.writeFloat(windDirection);
        parcel.writeFloat(clouds);
        parcel.writeString(iconCode);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date=" + date +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", clouds=" + clouds +
                ", iconCode='" + iconCode + '\'' +
                '}';
    }
}
