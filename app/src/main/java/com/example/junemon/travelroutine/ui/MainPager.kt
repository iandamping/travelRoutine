package com.example.junemon.travelroutine.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R

class MainPager : Fragment() {
    lateinit var vpMainPager: ViewPager
    lateinit var tabMainPager: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = inflater.inflate(R.layout.activity_main_pager, container, false)
        vpMainPager = views.findViewById(R.id.vpMainPage) as ViewPager
        tabMainPager = views.findViewById(R.id.tabMainPage) as TabLayout
        tabMainPager.setupWithViewPager(vpMainPager)
        vpMainPager.adapter = buildAdapter()
        return views
    }

    fun buildAdapter(): PagerAdapter {
        return SimpleMainPager(childFragmentManager)
    }
}