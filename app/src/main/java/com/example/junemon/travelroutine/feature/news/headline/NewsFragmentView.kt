package com.example.junemon.travelroutine.feature.news.headline

import com.example.junemon.travelroutine.base.BaseFragmentsViews
import com.example.junemon.travelroutine.network.model.PersonalNews

interface NewsFragmentView : BaseFragmentsViews {
    fun newsHeadline(data: List<PersonalNews.Article>?)
}