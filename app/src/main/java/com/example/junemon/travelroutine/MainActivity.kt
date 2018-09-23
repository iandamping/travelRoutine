package com.example.junemon.travelroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.junemon.travelroutine.feature.output.OutputFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.InputMenu -> {
                    loadOutputFragment(savedInstanceState)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottom_navigation.selectedItemId = R.id.InputMenu
    }


    private fun loadOutputFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, OutputFragment(), OutputFragment::class.java.simpleName)
                    .commit()
        }
    }


//
//    private fun loadTeamFragment(savedInstanceState: Bundle?) {
//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.main_container, TeamFragment().newInstance(data.strLeague), LastMatchFragment::class.java.simpleName)
//                    .commit()
//        }
//    }
}
