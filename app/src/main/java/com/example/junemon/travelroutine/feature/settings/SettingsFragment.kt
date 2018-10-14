package com.example.junemon.travelroutine.feature.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.example.junemon.travelroutine.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.pref_travel_routine)
    }

//    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
//        var status: Boolean = p0!!.getBoolean(getString(R.string.enabled_night_mode_key), resources.getBoolean(R.bool.pref_night_mode))
//        if (p1?.equals(getString(R.string.enabled_night_mode_key))!!) {
//            initNightMode(status)
//        } else if (p0 is EditTextPreference) {
//            val value = p0.getString(p0.getKey(), "")
//            setPreferenceSummary(p0, value)
//        }
//    }
//
//    private fun initNightMode(status: Boolean) {
//        if (status) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else if (!status) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//    }
//
//    private fun setPreferenceSummary(preference: Preference, value: String) {
//        if (preference is ListPreference) {
//            // For list preferences, figure out the label of the selected value
//            val listPreference = preference as ListPreference
//            val prefIndex = listPreference.findIndexOfValue(value)
//            if (prefIndex >= 0) {
//                // Set the summary to that label
//                listPreference.setSummary(listPreference.getEntries()[prefIndex])
//            }
//        } else if (preference is EditTextPreference) {
//            // For EditTextPreferences, set the summary to the value's simple string representation.
//            preference.setSummary(value)
//        }
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        preferenceScreen.sharedPreferences
//                .registerOnSharedPreferenceChangeListener(this)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        preferenceScreen.sharedPreferences
//                .unregisterOnSharedPreferenceChangeListener(this)
//    }
}