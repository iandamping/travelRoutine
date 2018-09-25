package com.example.junemon.travelroutine.feature.news.entertainment

import com.example.junemon.travelroutine.base.BaseFragmentsViews
import com.example.junemon.travelroutine.network.model.PersonalNewsEntertainment

interface NewsEntertainView : BaseFragmentsViews {
    fun getData(data: List<PersonalNewsEntertainment.Article>?)
}