package com.astroweather.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.astroweather.R;
import com.astroweather.util.AstroWeather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by mariusz on 11.04.16.
 */
public class SunFragment extends Fragment {
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            refresh();
        }
    };
    private TextView eastSunTimeTextView;
    private TextView eastSunAzimuthTextView;
    private TextView westSunTimeTextView;
    private TextView westSunAzimuthTextView;
    private TextView twilightSunTextView;
    private TextView dawnSunTextView;

    private void refresh() {
        try {
            update();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        handler.postDelayed(runnable, AstroWeather.REFRESH_RATE * 1000);
    }

    private void update() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AstroWeather.TIME_FORMAT);
        TextView time = (TextView) getActivity().findViewById(R.id.timeValue);
        if (time != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.setTime(simpleDateFormat.parse(String.valueOf(time.getText())));
            AstroDateTime astroDateTime =
                    new AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                            calendar.get(Calendar.ZONE_OFFSET) / AstroWeather.ZONE_OFFSET_DIVISOR, false);

            AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, AstroWeather.location);

            initTextViews();
            if (isTextViewInitialized()) {
                AstroCalculator.SunInfo sunInfo = astroCalculator.getSunInfo();
                updateTextViews(sunInfo);
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sun_fragment_layout, container, false);
    }

    private boolean isTextViewInitialized() {
        return (eastSunTimeTextView != null && eastSunAzimuthTextView != null && westSunTimeTextView != null && westSunAzimuthTextView != null && twilightSunTextView != null && dawnSunTextView != null);
    }

    private void updateTextViews(AstroCalculator.SunInfo sunInfo) {
        eastSunTimeTextView.setText(sunInfo.getSunrise().toString());
        eastSunAzimuthTextView.setText(sunInfo.getAzimuthRise() + "");
        westSunTimeTextView.setText(sunInfo.getSunset().toString());
        westSunAzimuthTextView.setText(sunInfo.getAzimuthSet() + "");
        twilightSunTextView.setText(sunInfo.getTwilightEvening().toString());
        dawnSunTextView.setText(sunInfo.getTwilightMorning().toString());
    }

    private void initTextViews() {
        eastSunTimeTextView = (TextView) getActivity().findViewById(R.id.eastSunTimeValue);
        eastSunAzimuthTextView = (TextView) getActivity().findViewById(R.id.eastSunAzimuthValue);
        westSunTimeTextView = (TextView) getActivity().findViewById(R.id.westSunTimeValue);
        westSunAzimuthTextView = (TextView) getActivity().findViewById(R.id.westSunAzimuthValue);
        twilightSunTextView = (TextView) getActivity().findViewById(R.id.twilightSunValue);
        dawnSunTextView = (TextView) getActivity().findViewById(R.id.dawnSunValue);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.removeCallbacks(runnable);
        refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
