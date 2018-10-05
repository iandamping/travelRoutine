package com.example.junemon.travelroutine.feature.tags

import com.example.junemon.travelroutine.base.BaseFragmentsViews
import com.example.junemon.travelroutine.database.model.PersonalTags

interface TagsView : BaseFragmentsViews {
    fun getData(data: List<PersonalTags>?)
}