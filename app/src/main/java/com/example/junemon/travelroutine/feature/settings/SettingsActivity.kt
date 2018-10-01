package com.example.junemon.travelroutine.feature.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import com.example.junemon.travelroutine.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.NightAppTheme)
        } else setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
}