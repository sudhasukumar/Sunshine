package com.example.sudha.sunshine;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.ACTION_VIEW;



/**
 * Created by Sudha on 12/21/2014 at 5:22 AM.
 */
public class WeatherListViewOnItemClickListener implements AdapterView.OnItemClickListener
{
    private final static String LOG_TAG = WeatherListViewOnItemClickListener.class.getSimpleName();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        HashMap<String, String> weatherDataOnClickHashMap = new WeatherDataParser().getOnItemClickWeatherDataFromJson(position);

        Toast weatherDetailsForItemClick = Toast.makeText(parent.getContext(),weatherDataOnClickHashMap.toString(),Toast.LENGTH_LONG );
        weatherDetailsForItemClick.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);

        weatherDetailsForItemClick.show();
        Log.v( LOG_TAG, "Toast is shown");

        Intent showWeatherDetailIntent = new Intent(view.getContext(), DetailActivity.class);
        showWeatherDetailIntent.addCategory(ACTION_VIEW);
        showWeatherDetailIntent.setType("text/plain");


        for(Map.Entry<String, String> entry : weatherDataOnClickHashMap.entrySet())
        {
            //Log.v( LOG_TAG, "Key : %s and Value: %s %n" + entry.getKey()+ entry.getValue());
            showWeatherDetailIntent.putExtra(entry.getKey(),entry.getValue());
        }
        view.getContext().startActivity(showWeatherDetailIntent);

    }
}
























