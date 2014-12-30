package com.example.sudha.sunshine;

import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Sudha on 12/18/2014.
 * Fetches the forecast information in a background thread using AsyncTask from the OpenWeatherMap API url
 */
public class FetchWeatherTask extends AsyncTask<String, Void, List<String>>
{
    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    public onFetchWeatherTaskListener asyncResponseDelegate =null;
    List<String> weatherDataArrayList;

    public FetchWeatherTask()
    {
    }

    @Override
    protected List<String> doInBackground(String... params)
    {

        //from Github https://gist.github.com/anonymous/1c04bf2423579e9d2dcd
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String SCHEME = "http";
        final String AUTHORITY = "api.openweathermap.org";
        final String PATH1 = "data";
        final String PATH2 = "2.5";
        final String PATH3 = "forecast";
        final String PATH4 = "daily";

        final String MODE = "mode";
        final String UNITS = "units";
        final String COUNT = "cnt";
        final String ZIPCODE = "q";

        final String JSON = "json";
        //assign the user preferred unit type here
        final String UNIT_TYPE = params[1];
        final int numDays = 7;


        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            Builder weatherTaskUriBuilder = new Builder();

            weatherTaskUriBuilder.scheme(SCHEME);
            weatherTaskUriBuilder.authority(AUTHORITY);
            weatherTaskUriBuilder.appendPath(PATH1);
            weatherTaskUriBuilder.appendPath(PATH2);
            weatherTaskUriBuilder.appendPath(PATH3);
            weatherTaskUriBuilder.appendPath(PATH4);

            weatherTaskUriBuilder.appendQueryParameter(ZIPCODE, params[0]);
            weatherTaskUriBuilder.appendQueryParameter(MODE, JSON);
            weatherTaskUriBuilder.appendQueryParameter(UNITS, "metric");
            weatherTaskUriBuilder.appendQueryParameter(COUNT, Integer.toString(numDays));

            String weatherTaskUrlString = weatherTaskUriBuilder.build().toString();
            Log.v(LOG_TAG, "Weather API URL String " +  weatherTaskUrlString);


            //http://api.openweathermap.org/data/2.5/forecast/daily?q=08902%2CUSA&mode=json&units=metric&cnt=7
            //http://api.openweathermap.org/data/2.5/forecast/daily?q=74120,USA&mode=json&units=metric&cnt=7
            //URL weatherTaskUrl = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
            URL weatherTaskUrl = new URL(weatherTaskUrlString);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) weatherTaskUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            // Will contain the raw JSON response as a string.


            if (inputStream != null)
            {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                    buffer.append("\n");
                }
            }

            if (buffer.length() != 0)
            {
                WeatherDataHolder.setWeatherDataFromApiCall(buffer.toString());
                weatherDataArrayList = new WeatherDataParser().getWeatherListDataFromJson(UNIT_TYPE);
                //Log.v( LOG_TAG , "buffer.toString() : " + buffer.toString());
                Log.v( LOG_TAG , "UNIT_TYPE in FetchWeatherTask : " + UNIT_TYPE);
            }



        } catch (IOException e)
        {
            Log.e(LOG_TAG, "Error ", e);
        }
        finally
        {
            if (urlConnection != null)
                urlConnection.disconnect();

            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (final IOException e)
                {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return weatherDataArrayList;
    }

    @Override
    protected void onPostExecute(List<String> weatherDataArrayListresult)
    {
        asyncResponseDelegate.onFetchWeatherTask(weatherDataArrayListresult);
    }
}
