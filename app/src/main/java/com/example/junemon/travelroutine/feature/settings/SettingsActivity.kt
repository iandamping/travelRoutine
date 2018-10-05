package com.example.junemon.travelroutine.feature.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import com.example.junemon.travelroutine.R
import org.jetbrains.anko.startActivity

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.NightAppTheme)
        } else setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupSharedPref()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            return true;
        }
        return false;

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p1?.equals(getString(R.string.enabled_night_mode_key))!!) {
            initNightMode(p0?.getBoolean(getString(R.string.enabled_night_mode_key),
                    resources.getBoolean(R.bool.pref_night_mode))!!)
        }
    }

    private fun setupSharedPref() {
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        initNightMode(sharedPref.getBoolean(getString(R.string.enabled_night_mode_key), resources.getBoolean(R.bool.pref_night_mode)))
        sharedPref.registerOnSharedPreferenceChangeListener(this)

    }

    private fun initNightMode(status: Boolean) {
        if (status) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if (!status) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
    }


}