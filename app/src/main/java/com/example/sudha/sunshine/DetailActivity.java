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

/**
 * Created by Sudha on 12/24/2014 at 2:09 AM.
 */
public class DetailActivity extends ActionBarActivity
{
    public static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null)
        {
            DetailForecastFragment detailForecastFragment = new DetailForecastFragment();
            detailForecastFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail, detailForecastFragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
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
        /*else if (id == R.id.action_share)
        {
            Log.v(LOG_TAG, "User would like to share weather details");
            return false;
        }*/
        return super.onOptionsItemSelected(item);

    }
    private void openPreferredLocationMap()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String userZipcode = sharedPrefs.getString(getString(R.string.location_key), getString(R.string.default_location));


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
