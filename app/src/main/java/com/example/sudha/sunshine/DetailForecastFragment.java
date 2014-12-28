package com.example.sudha.sunshine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Sudha on 12/24/2014 at 5:21 AM.
 */
public class DetailForecastFragment extends android.support.v4.app.Fragment
{
    public static final String LOG_TAG = DetailForecastFragment.class.getSimpleName();
    private HashMap<String, String> reconstructedFromIntentWeatherDataHashMap = new HashMap<>();
    private static final String DATE = "Date" ;
    private static final String DAY = "Day" ;
    private static final String MIN = "Min" ;
    private static final String MAX = "Max" ;
    private static final String NIGHT = "Night" ;
    private static final String EVENING = "Evening" ;
    private static final String MORNING = "Morning" ;
    private static final String PRESSURE = "Pressure" ;
    private static final String HUMIDITY = "Humidity" ;
    private static final String ID = "Id" ;
    private static final String MAIN = "Main" ;
    private static final String RAIN = "Rain" ;
    private static final String DESCRIPTION = "Description" ;
    private static final String ICON = "Icon" ;
    private static final String SPEED = "Speed" ;
    private static final String DEG = "Deg" ;
    private static final String CLOUDS = "Clouds" ;

    TextView weatherDetailTextView;

    public DetailForecastFragment()
    {

    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        Bundle intentExtrasBundle = this.getArguments();
        if(intentExtrasBundle != null)
        {
            reconstructedFromIntentWeatherDataHashMap.put(DATE,intentExtrasBundle.getString(DATE));
            reconstructedFromIntentWeatherDataHashMap.put(DAY,intentExtrasBundle.getString(DAY));
            reconstructedFromIntentWeatherDataHashMap.put(MIN,intentExtrasBundle.getString(MIN));
            reconstructedFromIntentWeatherDataHashMap.put(MAX,intentExtrasBundle.getString(MAX));
            reconstructedFromIntentWeatherDataHashMap.put(NIGHT,intentExtrasBundle.getString(NIGHT));
            reconstructedFromIntentWeatherDataHashMap.put(EVENING,intentExtrasBundle.getString(EVENING));
            reconstructedFromIntentWeatherDataHashMap.put(MORNING,intentExtrasBundle.getString(MORNING));
            reconstructedFromIntentWeatherDataHashMap.put(PRESSURE,intentExtrasBundle.getString(PRESSURE));
            reconstructedFromIntentWeatherDataHashMap.put(HUMIDITY,intentExtrasBundle.getString(HUMIDITY));
            reconstructedFromIntentWeatherDataHashMap.put(ID,intentExtrasBundle.getString(ID));

            String main = intentExtrasBundle.getString(MAIN);
            reconstructedFromIntentWeatherDataHashMap.put(MAIN,main);

            if (main.equalsIgnoreCase(RAIN))
                reconstructedFromIntentWeatherDataHashMap.put(RAIN,intentExtrasBundle.getString(RAIN));

            reconstructedFromIntentWeatherDataHashMap.put(DESCRIPTION,intentExtrasBundle.getString(DESCRIPTION));
            reconstructedFromIntentWeatherDataHashMap.put(ICON,intentExtrasBundle.getString(ICON));
            reconstructedFromIntentWeatherDataHashMap.put(SPEED,intentExtrasBundle.getString(SPEED));
            reconstructedFromIntentWeatherDataHashMap.put(DEG,intentExtrasBundle.getString(DEG));
            reconstructedFromIntentWeatherDataHashMap.put(CLOUDS,intentExtrasBundle.getString(CLOUDS));
            Log.v(LOG_TAG, "Completed reconstructing from HashMap");
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View weatherDetailView = inflater.inflate(R.layout.fragment_detail, container, false);
        weatherDetailTextView = (TextView) weatherDetailView.findViewById(R.id.Detailed_item_forecast_textview);
        weatherDetailTextView.setText(reconstructedFromIntentWeatherDataHashMap.toString());
        return weatherDetailView;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Log.v( LOG_TAG, "User Pressed Preferences from Detail Forecast Fragment");
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}







