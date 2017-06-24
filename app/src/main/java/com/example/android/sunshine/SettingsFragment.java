
package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;

/**
 * The SettingsFragment serves as the display for all of the user's settings. In Sunshine, the
 * user will be able to change their preference for units of measurement from metric to imperial,
 * set their preferred weather location, and indicate whether or not they'd like to see
 * notifications.
 * <p>
 * Please note: If you are using our dummy weather services, the location returned will always be
 * Mountain View, California.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements
        Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        Preference tempUnitPref = findPreference(getString(R.string.pref_units_key));
        bindPrefernceSummaryToValue(tempUnitPref);

        Preference locationPref = findPreference(getString(R.string.pref_location_key));
        bindPrefernceSummaryToValue(locationPref);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value = newValue.toString();
        if (preference instanceof ListPreference) {
            if (preference != null) {
                ListPreference listPreference = (ListPreference) preference;
                int indexOfValue = listPreference.findIndexOfValue(value);
                CharSequence[] labels = listPreference.getEntries();
                listPreference.setSummary(labels[indexOfValue]);
                return true;
            }
        } else if (preference instanceof EditTextPreference) {
            preference.setSummary(value);
        }
        return false;
    }

    public void bindPrefernceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String prefString = sharedPreferences.getString(preference.getKey(), "");
        onPreferenceChange(preference, prefString);

    }
}