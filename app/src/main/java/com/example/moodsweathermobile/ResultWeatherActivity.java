/*
Author: Thanos Moschou
Description: This is a simple weather app that user Open Meteo API.
It is the same that I made with JavaFX but is for mobile.

API requests are made using Volley library
 */

package com.example.moodsweathermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;

public class ResultWeatherActivity extends AppCompatActivity
{
    private Intent previousIntent;
    private View backgroundView;
    private ImageView backButton;

    private int currentWeatherCode;
    private String currentTemp, minTemp, maxTemp, city, sunrise, sunset, uvIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_weather);

        previousIntent = getIntent();

        backgroundView = findViewById(R.id.resultWeatherActivity);

        backButton = findViewById(R.id.backButton);
        backButton.setClickable(true);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        city = previousIntent.getStringExtra("cityName");
        currentWeatherCode = previousIntent.getIntExtra("weatherCode", -1);
        currentTemp = previousIntent.getStringExtra("currentTemp");
        minTemp = previousIntent.getStringExtra("minTemp");
        maxTemp = previousIntent.getStringExtra("maxTemp");
        sunrise = previousIntent.getStringExtra("sunrise");
        sunset = previousIntent.getStringExtra("sunset");
        uvIndex = previousIntent.getStringExtra("uvIndex");

        checkDataAndPlaceThemToTheActivity();
    }

    private void checkDataAndPlaceThemToTheActivity()
    {
        TextView cityName = findViewById(R.id.cityName);
        TextView weatherWordTxt = findViewById(R.id.weatherWordTxt);
        TextView temperatureTxt = findViewById(R.id.temperatureTxt);
        TextView dayTxt = findViewById(R.id.dayTxt);
        TextView dateTxt = findViewById(R.id.dateTxt);
        TextView minTempTxt = findViewById(R.id.minTempTxt);
        TextView maxTempTxt = findViewById(R.id.maxTempTxt);
        TextView sunriseTxt = findViewById(R.id.sunriseTxt);
        TextView sunsetTxt = findViewById(R.id.sunsetTxt);
        TextView uvIndexTxt = findViewById(R.id.uvIndexTxt);
        ImageView weatherIcon = findViewById(R.id.weatherIcon);

        boolean ok = false;

        if(currentWeatherCode <= 1)
        {
            backgroundView.setBackground(getResources().getDrawable(R.drawable.sunbackground));
            weatherWordTxt.setText(getString(R.string.sun));
            weatherIcon.setImageResource(R.drawable.sun);
            ok = true;
        }
        else if(currentWeatherCode <= 3)
        {
            backgroundView.setBackground(getResources().getDrawable(R.drawable.cloudybackground));
            weatherWordTxt.setText(getString(R.string.clouds));
            weatherIcon.setImageResource(R.drawable.cloud);
            ok = true;
        }
        else if(currentWeatherCode <= 67 || (currentWeatherCode >= 80 && currentWeatherCode <= 82))
        {
            backgroundView.setBackground(getResources().getDrawable(R.drawable.rainbackground));
            weatherWordTxt.setText(getString(R.string.rain));
            weatherIcon.setImageResource(R.drawable.rain);
            ok = true;
        }
        else if(currentWeatherCode <= 77 || (currentWeatherCode >= 85 && currentWeatherCode <= 86))
        {
            backgroundView.setBackground(getResources().getDrawable(R.drawable.snowbackground));
            weatherWordTxt.setText(getString(R.string.snow));
            weatherIcon.setImageResource(R.drawable.snow);
            ok = true;
        }
        else
        {
            cityName.setText(getString(R.string.unknownWeatherData));
            ok = false;
        }

        if(ok)
        {
            cityName.setText(city);
            temperatureTxt.setText(currentTemp + "°C");
            dayTxt.setText(LocalDate.now().getDayOfWeek().toString());
            dateTxt.setText(LocalDate.now().toString());
            minTempTxt.setText(minTemp + "°C");
            maxTempTxt.setText(maxTemp + "°C");
            sunriseTxt.setText(sunrise + "am");
            sunsetTxt.setText(sunset + "pm");
            uvIndexTxt.setText(uvIndex);
        }

    }
}