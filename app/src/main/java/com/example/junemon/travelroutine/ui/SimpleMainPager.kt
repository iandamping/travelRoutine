package com.example.junemon.travelroutine.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.junemon.travelroutine.feature.news.bussines.NewBussinesFragment
import com.example.junemon.travelroutine.feature.news.entertainment.NewsEntertainFragment
import com.example.junemon.travelroutine.feature.news.headline.NewsFragment

class SimpleMainPager(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val page_count: Int = 3
    private val tabTitle = arrayOf("Top Headlines", "Bussines", "Entertainment")
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment = NewsFragment()
        when (position) {
            1 -> fragment = NewBussinesFragment()
            2 -> fragment = NewsEntertainFragment()
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