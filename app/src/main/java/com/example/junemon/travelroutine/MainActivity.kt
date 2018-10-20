package com.example.junemon.travelroutine

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import com.example.junemon.travelroutine.feature.event.output.OutputEventFragment
import com.example.junemon.travelroutine.feature.items.output.OutputFragment
import com.example.junemon.travelroutine.feature.routine.output.OutputRoutineFragment
import com.example.junemon.travelroutine.feature.settings.SettingsActivity
import com.example.junemon.travelroutine.feature.tags.TagsFragment
import com.example.junemon.travelroutine.helper.animations.RevealAnimation
import com.example.junemon.travelroutine.helper.networkchecker.LiveDataNetworkListener
import com.example.junemon.travelroutine.repositories.News.NewsRepositories
import com.example.junemon.travelroutine.ui.MainPager
import com.example.junemon.travelroutine.ui.WeeklyRemindPager
import kotlinx.android.synthetic.main.item_drawer_layout.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var mRevealAnimation: RevealAnimation
    private lateinit var fragment: TagsFragment
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.DayAppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_drawer_layout)

        initNavView()
        loadOutputItemsFragment(savedInstanceState)
        nav_view.setCheckedItem(R.id.NavInputMenuNav)


    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.NavInputMenuNav -> loadOutputItemsFragment(null)
            R.id.NavRoutineMenu -> loadOutputRoutinesFragment(null)
            R.id.NavEventMenu -> loadOutputEventFragment(null)
            R.id.NavNewsMenu -> loadMainFragment(null)
//            R.id.NavEnterPref -> startActivity<SettingsActivity>()
            R.id.NavAddTags -> loadTagsFragment(null)
            R.id.NavWeeklyRemind -> loadWeeklyPagerFragment(null)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun loadOutputItemsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
//                    .setCustomAnimations(R.anim.exit_from_right, R.anim.enter_to_right, R.anim.exit_from_right, R.anim.enter_to_right)
                    .replace(R.id.main_fragment_container, OutputFragment(), OutputFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadOutputRoutinesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
//                    .setCustomAnimations(R.anim.exit_from_right, R.anim.enter_to_right, R.anim.exit_from_right, R.anim.enter_to_right)
                    .replace(R.id.main_fragment_container, OutputRoutineFragment(), OutputRoutineFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadOutputEventFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
//                    .setCustomAnimations(R.anim.exit_from_right, R.anim.enter_to_right, R.anim.exit_from_right, R.anim.enter_to_right)
                    .replace(R.id.main_fragment_container, OutputEventFragment(), OutputEventFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadMainFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, MainPager(), MainPager::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadWeeklyPagerFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, WeeklyRemindPager(), WeeklyRemindPager::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadTagsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
//                    .setCustomAnimations(R.anim.exit_from_right, R.anim.enter_to_right, R.anim.exit_from_right, R.anim.enter_to_right)
                    .replace(R.id.main_fragment_container, TagsFragment(), TagsFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun internetChecker() {
        val connectionLiveData = LiveDataNetworkListener(this)
        connectionLiveData.observe(this, Observer { status ->
            if (status!!) {
                NewsRepositories.getAllNews(
                        NewsRepositories.STATE_OF_PULLED, resources.getString(R.string.category_entertainment), resources.getString(R.string.category_news), resources.getString(R.string.country_news)
                        , resources.getString(R.string.api_source_news), resources.getString(R.string.api_key_news))

            } else if (!status) {
                alert("no Internet Connection") {
                    yesButton { }
                }.show()
            }
        })
    }

    private fun initNavView() {
        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_closed)
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)
        toolbar_main.title = "Travel Routine"
        drawer_layout.addDrawerListener(toggle)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        nav_view.setNavigationItemSelectedListener(this)
        fragment = TagsFragment()
        fragment.initiateFirst(this)
        internetChecker()
        setupSharedPref()
//        initAnimationView()

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p1?.equals(getString(R.string.enabled_night_mode_key))!!) {
            initNightMode(p0?.getBoolean(getString(R.string.enabled_night_mode_key),
                    resources.getBoolean(R.bool.pref_night_mode))!!)
        }
//        else if (p1.equals(R.string.pref_size_key)) {
//            initTextSize(p0?.getString(getString(R.string.pref_size_key), "1.0")!!.toFloat())
//        }
    }

    private fun setupSharedPref() {
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        initNightMode(sharedPref.getBoolean(getString(R.string.enabled_night_mode_key), resources.getBoolean(R.bool.pref_night_mode)))
//        initTextSize(sharedPref.getString(getString(R.string.pref_size_key), "1.0")!!.toFloat())
        sharedPref.registerOnSharedPreferenceChangeListener(this)

    }

//    private fun initTextSize(size: Float): Float = 50F * size

    private fun initNightMode(status: Boolean) {
        if (status) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if (!status) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
//            mRevealAnimation.unRevealActivity()
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NewsRepositories.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun initAnimationView() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        val i: Intent = intent
        mRevealAnimation = RevealAnimation(drawer_layout, i, this)
    }
}
