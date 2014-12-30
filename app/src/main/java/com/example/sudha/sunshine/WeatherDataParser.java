package com.example.sudha.sunshine;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



/**
 * Created by Sudha on 12/20/2014 at 3:02 AM.
 */
public class WeatherDataParser
{
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

    /**
         * Given a string of the form returned by the api call:
         * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
         * retrieve the maximum temperature for the day indicated by dayIndex
         * (Note: 0-indexed, so 0 would refer to the first day).
         */
    private final String LOG_TAG = WeatherDataParser.class.getSimpleName();

    List<String> weatherDataArrayList;

    public WeatherDataParser()
    {
    }

    protected List<String> getWeatherListDataFromJson(String unitType)
    {
        try
        {
            JSONObject weatherInputJsonObject = new JSONObject(WeatherDataHolder.getWeatherDataFromApiCall());

            JSONArray weatherDaysArray = weatherInputJsonObject.getJSONArray("list");
            Log.v(LOG_TAG , " Weather Data for number of days : "+ weatherDaysArray.length() + " in UNIT_TYPE : " + unitType);

            weatherDataArrayList = new ArrayList<>();

            for (int i=0; i < weatherDaysArray.length(); i++)
            {
                JSONObject weatherJsonDayObject = weatherDaysArray.getJSONObject(i);

                Long dt = weatherJsonDayObject.getLong("dt");
                //Log.v(LOG_TAG , " dt : "+ dt);
                StringBuilder weatherData = new StringBuilder();
                weatherData.append(getReadableDateString(dt));

                JSONArray weatherArrayForTheDay = weatherJsonDayObject.getJSONArray("weather");

                String main = weatherArrayForTheDay.getJSONObject(0).getString("main");
                //Log.v(LOG_TAG , " main : "+ main);

                weatherData.append(" - ");
                weatherData.append(main);

                JSONObject weatherJsonTempObject = weatherJsonDayObject.getJSONObject("temp");

                Double min = weatherJsonTempObject.getDouble("min");
                //Log.v(LOG_TAG , " min : "+ min);

                Double max = weatherJsonTempObject.getDouble("max");
                //Log.v(LOG_TAG , " max : "+ max);

                weatherData.append(" - ");

                //Important conversion of temperature units based on user preferences

                if (unitType.equals("imperial"))
                {
                    weatherData.append(formatHighLows(completeTemperatureConversion(max), completeTemperatureConversion(min)));
                }
                else
                    weatherData.append(formatHighLows(max,min));

                //Log.v(LOG_TAG , " weatherData : "+ weatherData.toString());

                weatherDataArrayList.add(weatherData.toString());
            }

        }
        catch(JSONException jsone)
        {
            Log.e(LOG_TAG , jsone.getMessage());

        }
        return weatherDataArrayList;
    }

    public HashMap<String, String> getOnItemClickWeatherDataFromJson(int position,String unitType)
    {
        HashMap<String, String> OnListItemClickWeatherData = new HashMap<>();
        try
        {
            JSONObject weatherInputJsonObject = new JSONObject(WeatherDataHolder.getWeatherDataFromApiCall());

            JSONObject weatherJsonDayObject = weatherInputJsonObject.getJSONArray("list").getJSONObject(position);

            Long dt = weatherJsonDayObject.getLong("dt");
            OnListItemClickWeatherData.put(DATE,getReadableDateString(dt));

            JSONObject weatherJsonTempObject = weatherJsonDayObject.getJSONObject("temp");
            if (unitType.equals("imperial"))
            {
                long day = formatTemp(completeTemperatureConversion(weatherJsonTempObject.getDouble("day")));
                OnListItemClickWeatherData.put(DAY, String.valueOf(day));
                long min = formatTemp(completeTemperatureConversion(weatherJsonTempObject.getDouble("min")));
                OnListItemClickWeatherData.put(MIN, String.valueOf(min));
                long max = formatTemp(completeTemperatureConversion(weatherJsonTempObject.getDouble("max")));
                OnListItemClickWeatherData.put(MAX, String.valueOf(max));
                long night = formatTemp(completeTemperatureConversion(weatherJsonTempObject.getDouble("night")));
                OnListItemClickWeatherData.put(NIGHT, String.valueOf(night));
                long eve = formatTemp(completeTemperatureConversion(weatherJsonTempObject.getDouble("eve")));
                OnListItemClickWeatherData.put(EVENING, String.valueOf(eve));
                long morn = formatTemp(completeTemperatureConversion(weatherJsonTempObject.getDouble("morn")));
                OnListItemClickWeatherData.put(MORNING, String.valueOf(morn));
            }
            else
            {
                OnListItemClickWeatherData.put(DAY, String.valueOf(weatherJsonTempObject.getDouble("day")));
                OnListItemClickWeatherData.put(MIN, String.valueOf(weatherJsonTempObject.getDouble("min")));
                OnListItemClickWeatherData.put(MAX, String.valueOf(weatherJsonTempObject.getDouble("max")));
                OnListItemClickWeatherData.put(NIGHT, String.valueOf(weatherJsonTempObject.getDouble("night")));
                OnListItemClickWeatherData.put(EVENING, String.valueOf(weatherJsonTempObject.getDouble("eve")));
                OnListItemClickWeatherData.put(MORNING, String.valueOf(weatherJsonTempObject.getDouble("morn")));

            }


            OnListItemClickWeatherData.put(PRESSURE, String.valueOf(weatherJsonDayObject.getLong("pressure")));
            OnListItemClickWeatherData.put(HUMIDITY, String.valueOf(weatherJsonDayObject.getLong("humidity")));

            OnListItemClickWeatherData.put(ID, weatherJsonDayObject.getJSONArray("weather").getJSONObject(0).getString("id"));

            String main = weatherJsonDayObject.getJSONArray("weather").getJSONObject(0).getString("main");
            Log.v(LOG_TAG , " main : "+ main);

            OnListItemClickWeatherData.put(MAIN, main);

            if (main.equalsIgnoreCase("Rain"))
                OnListItemClickWeatherData.put(RAIN, String.valueOf(weatherJsonDayObject.getLong("rain")));

            OnListItemClickWeatherData.put(DESCRIPTION, weatherJsonDayObject.getJSONArray("weather").getJSONObject(0).getString("description"));
            OnListItemClickWeatherData.put(ICON, weatherJsonDayObject.getJSONArray("weather").getJSONObject(0).getString("icon"));

            OnListItemClickWeatherData.put(SPEED, String.valueOf(weatherJsonDayObject.getLong("speed")));
            OnListItemClickWeatherData.put(DEG, String.valueOf(weatherJsonDayObject.getLong("deg")));
            OnListItemClickWeatherData.put(CLOUDS, String.valueOf(weatherJsonDayObject.getLong("clouds")));


            Log.v(LOG_TAG, OnListItemClickWeatherData.toString());

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return OnListItemClickWeatherData;
    }

    /* The date/time conversion code is going to be moved outside the asynctask later,
     * so for convenience we're breaking it out into its own method now.
     */
    private String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date);
    }

     /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low)
    {
        // For presentation, assume the user doesn't care about tenths of a degree.
        return (Math.round(high) + "/" + Math.round(low));
    }

    private long formatTemp(double temp)
    {
        // For presentation, assume the user doesn't care about tenths of a degree.
        return (Math.round(temp));
    }

    //This method converts metric temp to Imperial temperature if the user preference is set to Imperial
    private double completeTemperatureConversion(double temp)
    {
        //Log.v(LOG_TAG, "Weather Temp Conversion from metric to imperial ");
        temp = (temp * 1.8) +32;
        return temp;

    }

}
