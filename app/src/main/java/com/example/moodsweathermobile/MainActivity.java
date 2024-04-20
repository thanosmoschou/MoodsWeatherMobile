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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    private int currentWeatherCode;
    private String city, latitude, longitude, currentTemp, minTemp, maxTemp, sunrise, sunset, uvIndex;
    private EditText userInput;
    private ImageButton searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.userInput);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                city = userInput.getText().toString();
                if(!Character.isUpperCase(city.charAt(0)))
                    city = Character.toUpperCase(city.charAt(0)) + city.substring(1);

                //make a request...if city does not exist in api then show a toast
                checkLatitudeLongitude();
            }
        });
    }


    private void checkLatitudeLongitude()
    {
        String geocodingUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + city +
                "&count=1&language=en&format=json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, geocodingUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    if(jsonArray.length() > 0)
                    {
                        latitude = jsonArray.getJSONObject(0).getString("latitude");
                        longitude = jsonArray.getJSONObject(0).getString("longitude");

                        makeWeatherRequest();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "City does not exist", Toast.LENGTH_SHORT).show();
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error getting the city", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }


    private void makeWeatherRequest()
    {
        String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude
                + "&current=temperature_2m,weather_code&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max&timezone=auto&forecast_days=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, weatherUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("current");
                    currentWeatherCode = Integer.parseInt(jsonObject.get("weather_code").toString());
                    currentTemp = jsonObject.get("temperature_2m").toString();

                    jsonObject = new JSONObject(response).getJSONObject("daily");
                    maxTemp = jsonObject.getJSONArray("temperature_2m_max").get(0).toString();
                    minTemp = jsonObject.getJSONArray("temperature_2m_min").get(0).toString();
                    sunrise = jsonObject.getJSONArray("sunrise").get(0).toString().substring(11);
                    sunset = jsonObject.getJSONArray("sunset").get(0).toString().substring(11);
                    uvIndex = jsonObject.getJSONArray("uv_index_max").get(0).toString();

                    Intent intent = new Intent(getApplicationContext(), ResultWeatherActivity.class);

                    intent.putExtra("cityName", city);
                    intent.putExtra("weatherCode", currentWeatherCode);
                    intent.putExtra("currentTemp", currentTemp);
                    intent.putExtra("minTemp", minTemp);
                    intent.putExtra("maxTemp", maxTemp);
                    intent.putExtra("sunrise", sunrise);
                    intent.putExtra("sunset", sunset);
                    intent.putExtra("uvIndex", uvIndex);

                    startActivity(intent);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error Getting Weather Data...", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}