package com.example.sudha.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ListForecastFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_mapLocation)
        {
            openPreferredLocationMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationMap()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String userZipcode = sharedPrefs.getString(getString(R.string.Location), getString(R.string.default_location));


        Uri geoLocation =  Uri.parse("geo:(0,0)").buildUpon().appendQueryParameter("q", userZipcode).build();
        Intent showLocationMapIntent = new Intent();
        showLocationMapIntent.setAction(Intent.ACTION_VIEW);
        showLocationMapIntent.setData(geoLocation);

        if (showLocationMapIntent.resolveActivity(getPackageManager()) != null)
        {
            Log.v(LOG_TAG, "Showing Map to User for Location :" + userZipcode);
            startActivity(showLocationMapIntent);

        }

    }


}

