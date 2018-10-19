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

class WeeklyRemindPager : Fragment() {
    lateinit var vpWeeklyPage: ViewPager
    lateinit var tabWeeklyPage: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = inflater.inflate(R.layout.activity_weekly_remind_pager, container, false)
        vpWeeklyPage = views.findViewById(R.id.vpWeeklyPage) as ViewPager
        tabWeeklyPage = views.findViewById(R.id.tabWeeklyPage) as TabLayout
        tabWeeklyPage.setupWithViewPager(vpWeeklyPage)
        vpWeeklyPage.adapter = buildAdapter()
        return views
    }

    fun buildAdapter(): PagerAdapter {
        return WeeklySimpleRemindPager(childFragmentManager)
    }
}