package com.example.sudha.sunshine;

/**
 * Created by Sudha on 12/27/2014 at 3:18 AM.
 */

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener , Preference.OnPreferenceClickListener
{

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new WeatherPreferenceFragment())
                .commit();



    }

    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }
    @Override
    public boolean onPreferenceClick(Preference preference)
    {
        bindPreferenceSummaryToValue(preference);
        return false;
    }
    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
     */
    private void bindPreferenceSummaryToValue(Preference preference)
    {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }



    public boolean onPreferenceChange(Preference preference, Object value)
    {
        String stringValue = value.toString();

        if (preference instanceof ListPreference)
        {
            // For list preferences_location, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0)
            {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        else
        {
            // For other preferences_location, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }


    public static class WeatherPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_location);
            addPreferencesFromResource(R.xml.preferences_units);

            String settings;
            Bundle extras = getArguments();


            if ( extras != null)
            {
                settings = getArguments().getString("Key");
                if ("Location".equals(settings))
                    addPreferencesFromResource(R.xml.preferences_location);
                else if ("Units".equals(settings))
                    addPreferencesFromResource(R.xml.preferences_units);

            }


        }
    }

}
