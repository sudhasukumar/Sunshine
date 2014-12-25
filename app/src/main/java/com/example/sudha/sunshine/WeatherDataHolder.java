package com.example.sudha.sunshine;

/**
 * Created by Sudha on 12/21/2014 at 5:52 AM.
 */
class WeatherDataHolder
{
    private static String weatherDataFromApiCall = null;

    public static String getWeatherDataFromApiCall()
    {
        return weatherDataFromApiCall;
    }

    public static void setWeatherDataFromApiCall(String weatherDataFromApiCall)
    {
        WeatherDataHolder.weatherDataFromApiCall = weatherDataFromApiCall;
    }

}
