package com.example.sudha.sunshine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sudha on 12/18/2014 at 8:52 PM.
 */
public class ListForecastFragment extends android.support.v4.app.Fragment implements AsyncResponseInterface
{
    /**
     * A Forecast fragment containing a Listview to display weather using FetchWeatherTask
     */
    public static final String LOG_TAG = ListForecastFragment.class.getSimpleName();

    ArrayAdapter <String> stringArrayAdapter;
    FetchWeatherTask fetchWeatherTask;

    //final String INPUT_ZIPCODE = "94043";
    final String INPUT_ZIPCODE = "08902,USA";

    public ListForecastFragment()
    {

    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<String> simpleWeatherDataArrayList = new ArrayList<>(6);
        simpleWeatherDataArrayList.add(0, "Today-static-83/63");
        simpleWeatherDataArrayList.add(1, "Tue-Foggy-70/46");
        simpleWeatherDataArrayList.add(2, "Wed-Cloudy-72/63");
        simpleWeatherDataArrayList.add(3, "Thurs-Rainy-64/51");
        simpleWeatherDataArrayList.add(4, "Fri-Foggy-70/46");
        simpleWeatherDataArrayList.add(5, "Sat-Sunny-76/68");

        stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast_textview, R.id.list_item_forecast_textview, simpleWeatherDataArrayList);

        ListView listview_forecast = (ListView) rootView.findViewById(R.id.fragment_listview_forecast);
        listview_forecast.setAdapter(stringArrayAdapter);

        listview_forecast.setClickable(true);
        listview_forecast.setOnItemClickListener(new WeatherListViewOnItemClickListener());

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_listforecastfragment, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();


        Log.v(LOG_TAG , id + " : " + "Forecast Fragment menu option has been selected for " + INPUT_ZIPCODE);
        if (id == R.id.action_refresh)
        {
            fetchWeatherTask = new FetchWeatherTask();
            fetchWeatherTask.delegate = this;

            fetchWeatherTask.execute(INPUT_ZIPCODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(List<String> ArrayListOfStrings)
    {
        stringArrayAdapter.clear();
        stringArrayAdapter.addAll(ArrayListOfStrings);
        /* stringArrayAdapter.notifyDataSetChanged(); This will be called internally by the framework when the data changes */
        Log.v(LOG_TAG, "Check if Data has changed in List view");
    }
}

