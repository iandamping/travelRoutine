package com.example.junemon.travelroutine.feature.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.example.junemon.travelroutine.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.pref_travel_routine)
    }
}