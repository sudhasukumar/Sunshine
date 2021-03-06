package com.example.sudha.sunshine;

/**
 * Created by Sudha on 12/27/2014 at 3:18 AM.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * A {@link PreferenceActivity} that presents a set of application settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener
{
    private static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.unit_key)));

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        //String INPUT_ZIPCODE = sharedPrefs.getString(getString(R.string.location_key), getString(R.string.default_location));
        //String UNIT_TYPE = sharedPrefs.getString(getString(R.string.unit_key), getString(R.string.default_unit));

        //Log.v(LOG_TAG, "Preferences loaded from XML in SettingsActivity : " + sharedPrefs.getAll().toString());

        setContentView(R.layout.activity_settings);
        //import android.support.v7.widget.Toolbar;
        Toolbar actionbar = (Toolbar) findViewById(R.id.mytoolbar);
        actionbar.setTitle("Settings");
        actionbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        actionbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                SettingsActivity.this.finish();
                                                                            }
        });

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
            // For list preferences, look up the correct display value in
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
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }
}
