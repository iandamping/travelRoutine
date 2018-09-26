package com.example.junemon.travelroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.junemon.travelroutine.feature.items.output.OutputFragment
import com.example.junemon.travelroutine.feature.routine.output.OutputRoutineFragment
import com.example.junemon.travelroutine.repositories.News.NewsRepositories
import com.example.junemon.travelroutine.ui.MainPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NewsRepositories.getAllNews(
                NewsRepositories.STATE_OF_PULLED, resources.getString(R.string.category_entertainment), resources.getString(R.string.category_news), resources.getString(R.string.country_news)
                , resources.getString(R.string.api_source_news), resources.getString(R.string.api_key_news))

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.InputMenu -> {
                    loadOutputItemsFragment(savedInstanceState)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.RoutineMenu -> {
                    loadOutputRoutinesFragment(savedInstanceState)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.NewsMenu -> {
                    loadMainFragment(savedInstanceState)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottom_navigation.selectedItemId = R.id.InputMenu
    }


    private fun loadOutputItemsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, OutputFragment(), OutputFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadOutputRoutinesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, OutputRoutineFragment(), OutputRoutineFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadMainFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MainPager(), MainPager::class.java.simpleName)
                    .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NewsRepositories.onDestroy()
    }
}
