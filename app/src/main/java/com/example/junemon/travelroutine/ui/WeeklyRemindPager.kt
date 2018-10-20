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
        smoothTransform(vpWeeklyPage)
        return views
    }

    fun smoothTransform(viewPager: ViewPager) {
        viewPager.setPageTransformer(false, object : ViewPager.PageTransformer {
            override fun transformPage(p0: View, p1: Float) {
                val pageWidht = viewPager.measuredWidth - viewPager.paddingLeft - viewPager.paddingRight
                val pageHeight = viewPager.height
                val paddingLeft = viewPager.paddingLeft
                val transformPos = (p0.getLeft() - (viewPager.scrollX + paddingLeft)).toFloat() / pageWidht
                val normalizedposition = Math.abs(Math.abs(transformPos) - 1)
                p0.setAlpha(normalizedposition + 0.5f);
                val max = -pageHeight / 10

                if (transformPos < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    p0.setTranslationY(0F);
                } else if (transformPos <= 1) { // [-1,1]
                    p0.setTranslationY(max * (1-Math.abs(transformPos)));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    p0.setTranslationY(0F);
                }
            }

        })
    }

    fun buildAdapter(): PagerAdapter {
        return WeeklySimpleRemindPager(childFragmentManager)
    }
}