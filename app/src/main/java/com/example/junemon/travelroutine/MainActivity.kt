package com.example.junemon.travelroutine

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.junemon.travelroutine.feature.items.output.OutputFragment
import com.example.junemon.travelroutine.feature.routine.output.OutputRoutineFragment
import com.example.junemon.travelroutine.helper.networkchecker.NetworkChangeListener
import com.example.junemon.travelroutine.repositories.News.NewsRepositories
import com.example.junemon.travelroutine.ui.MainPager
import kotlinx.android.synthetic.main.item_drawer_layout.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var networkChecker: NetworkChangeListener
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_drawer_layout)
        networkChecker = NetworkChangeListener()
        networkChecker.register(this)
        internetChecker()

        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_closed)
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)

        drawer_layout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        nav_view.setNavigationItemSelectedListener(this)
        loadOutputItemsFragment(savedInstanceState)
        nav_view.setCheckedItem(R.id.NavInputMenuNav)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.NavInputMenuNav -> loadOutputItemsFragment(null)
            R.id.NavRoutineMenu -> loadOutputRoutinesFragment(null)
            R.id.NavNewsMenu -> loadMainFragment(null)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun loadOutputItemsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction().setCustomAnimations(R.anim.exit_from_right, R.anim.enter_to_right, R.anim.exit_from_right, R.anim.enter_to_right)
                    .replace(R.id.main_fragment_container, OutputFragment(), OutputFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadOutputRoutinesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction().setCustomAnimations(R.anim.exit_from_right, R.anim.enter_to_right, R.anim.exit_from_right, R.anim.enter_to_right)
                    .replace(R.id.main_fragment_container, OutputRoutineFragment(), OutputRoutineFragment::class.java.simpleName)
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

    private fun internetChecker() {
        if (networkChecker.isConnected(this) == false) {
            alert("no Internet Connection") {
                yesButton { }
                noButton { }
            }.show()
        } else if (networkChecker.isConnected(this) == true) {
            NewsRepositories.getAllNews(
                    NewsRepositories.STATE_OF_PULLED, resources.getString(R.string.category_entertainment), resources.getString(R.string.category_news), resources.getString(R.string.country_news)
                    , resources.getString(R.string.api_source_news), resources.getString(R.string.api_key_news))

        }

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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NewsRepositories.onDestroy()
    }
}
