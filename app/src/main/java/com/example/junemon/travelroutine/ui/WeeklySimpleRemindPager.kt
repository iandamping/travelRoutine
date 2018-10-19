package com.example.junemon.travelroutine.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.junemon.travelroutine.feature.weeklyreminder.friday.FridayFragment
import com.example.junemon.travelroutine.feature.weeklyreminder.monday.MondayFragment
import com.example.junemon.travelroutine.feature.weeklyreminder.satuday.SaturdayFragment
import com.example.junemon.travelroutine.feature.weeklyreminder.sunday.SundayFragment
import com.example.junemon.travelroutine.feature.weeklyreminder.thursday.ThurdsayFragment
import com.example.junemon.travelroutine.feature.weeklyreminder.tuesday.TuesdayFragment
import com.example.junemon.travelroutine.feature.weeklyreminder.wednesday.WednesdayFragment

class WeeklySimpleRemindPager(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val page_count: Int = 7
    private val tabTitle = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    override fun getItem(p0: Int): Fragment {
        var fragment: Fragment = SundayFragment()
        when (p0) {
            1 -> fragment = MondayFragment()
            2 -> fragment = TuesdayFragment()
            3 -> fragment = WednesdayFragment()
            4 -> fragment = ThurdsayFragment()
            5 -> fragment = FridayFragment()
            6 -> fragment = SaturdayFragment()
        }
        return fragment
    }

    override fun getCount(): Int {
        return page_count
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitle[position]
    }
}