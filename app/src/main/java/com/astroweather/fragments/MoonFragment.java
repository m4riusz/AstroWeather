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
import java.util.Date;

/**
 * Created by mariusz on 11.04.16.
 */
public class MoonFragment extends Fragment {
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            refresh();
        }
    };
    private TextView eastTextView;
    private TextView westTextView;
    private TextView newMoonTextView;
    private TextView fullMoonTextView;
    private TextView moonStage;
    private TextView dayTextView;

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
            Date date = simpleDateFormat.parse(String.valueOf(time.getText()));
            AstroDateTime astroDateTime = new AstroDateTime(date.getYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getTimezoneOffset(), false);
            AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, AstroWeather.location);

            initTextViews();
            if (isTextViewInitialized()) {
                AstroCalculator.MoonInfo moonInfo = astroCalculator.getMoonInfo();
                updateTextViews(moonInfo);
            }
        }
    }

    private boolean isTextViewInitialized() {
        return (eastTextView != null && westTextView != null && newMoonTextView != null && fullMoonTextView != null && moonStage != null && dayTextView != null);
    }

    private void updateTextViews(AstroCalculator.MoonInfo moonInfo) {
        eastTextView.setText(moonInfo.getMoonrise().toString());
        westTextView.setText(moonInfo.getMoonset().toString());
        newMoonTextView.setText(moonInfo.getNextNewMoon().toString());
        fullMoonTextView.setText(moonInfo.getNextFullMoon().toString());
        moonStage.setText(moonInfo.getIllumination() + "");
        dayTextView.setText(moonInfo.getAge() + "");
    }

    private void initTextViews() {
        eastTextView = (TextView) getActivity().findViewById(R.id.eastValue);
        westTextView = (TextView) getActivity().findViewById(R.id.westValue);
        newMoonTextView = (TextView) getActivity().findViewById(R.id.newMoonValue);
        fullMoonTextView = (TextView) getActivity().findViewById(R.id.fullMoonValue);
        moonStage = (TextView) getActivity().findViewById(R.id.moonStageValue);
        dayTextView = (TextView) getActivity().findViewById(R.id.probeDayValue);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        runnable.run();
        return inflater.inflate(R.layout.moon_fragment_layout, container, false);
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
