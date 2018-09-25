package com.example.junemon.travelroutine.feature.news.bussines

import com.example.junemon.travelroutine.base.BaseFragmentsViews
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines

interface NewBussinesView : BaseFragmentsViews {
    fun getData(data: List<PersonalNewsBussines.Article>?)
}