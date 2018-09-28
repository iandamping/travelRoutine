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
import com.example.junemon.travelroutine.network.model.PersonalNews
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines
import com.example.junemon.travelroutine.network.model.PersonalNewsEntertainment

class MainPager : Fragment() {
    lateinit var vpMainPager: ViewPager
    lateinit var tabMainPager: TabLayout

    fun newInstance(news: ArrayList<PersonalNews.Article>?, bussines: ArrayList<PersonalNewsBussines.Article>?, entertain: ArrayList<PersonalNewsEntertainment.Article>?): MainPager {
        val bundle = Bundle()
        bundle.putParcelableArrayList("news", news)
        bundle.putParcelableArrayList("bussiness", bussines)
        bundle.putParcelableArrayList("entertain", entertain)
        val fragment = MainPager()
        fragment.setArguments(bundle)
        return fragment
    }

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