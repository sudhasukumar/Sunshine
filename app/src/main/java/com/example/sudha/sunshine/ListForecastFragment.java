package com.example.sudha.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
public class ListForecastFragment extends android.support.v4.app.Fragment implements onFetchWeatherTaskListener
{
    /**
     * A Forecast fragment containing a Listview to display weather using FetchWeatherTask
     */
    public static final String LOG_TAG = ListForecastFragment.class.getSimpleName();
    private static String UNIT_TYPE;

    ArrayAdapter <String> stringArrayAdapter;
    FetchWeatherTask fetchWeatherTask;

    //INPUT_ZIPCODE = "94043";
    private static String INPUT_ZIPCODE;
    SharedPreferences sharedPrefs;


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

        List<String> simpleWeatherDataArrayList = new ArrayList<>();
        /*simpleWeatherDataArrayList.add(0, "Today-static-83/63");
        simpleWeatherDataArrayList.add(1, "Tue-Foggy-70/46");
        simpleWeatherDataArrayList.add(2, "Wed-Cloudy-72/63");
        simpleWeatherDataArrayList.add(3, "Thurs-Rainy-64/51");
        simpleWeatherDataArrayList.add(4, "Fri-Foggy-70/46");
        simpleWeatherDataArrayList.add(5, "Sat-Sunny-76/68");*/

        stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast_textview, R.id.list_item_forecast_textview, simpleWeatherDataArrayList);

        ListView listview_forecast = (ListView) rootView.findViewById(R.id.fragment_listview_forecast);
        listview_forecast.setAdapter(stringArrayAdapter);

        listview_forecast.setClickable(true);
        listview_forecast.setOnItemClickListener(new WeatherListViewOnItemClickListener());

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        INPUT_ZIPCODE = sharedPrefs.getString(getString(R.string.location_key), getString(R.string.default_location));
        UNIT_TYPE = sharedPrefs.getString(getString(R.string.unit_key), getString(R.string.default_unit));

        //Log.v(LOG_TAG, "Preferences loaded from XML in ListForecastFragment : " + sharedPrefs.getAll().toString());

        updateWeather();
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

        if (id == R.id.action_refresh)
        {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather()
    {
        fetchWeatherTask = new FetchWeatherTask();
        fetchWeatherTask.asyncResponseDelegate = this;

        //Log.v(LOG_TAG , "Forecast Fragment menu option has been selected for " + INPUT_ZIPCODE + " : " + UNIT_TYPE);

        fetchWeatherTask.execute(INPUT_ZIPCODE,UNIT_TYPE);
    }

    @Override
    public void onFetchWeatherTask(List<String> ArrayListOfStrings)
    {
        stringArrayAdapter.clear();
        stringArrayAdapter.addAll(ArrayListOfStrings);
        //Log.v(LOG_TAG, "Check if Data has changed in List view");
    }


}

