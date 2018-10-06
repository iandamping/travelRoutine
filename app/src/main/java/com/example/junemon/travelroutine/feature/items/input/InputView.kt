package com.example.junemon.travelroutine.feature.items.input

import com.example.junemon.travelroutine.base.BaseViews
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalTags

interface InputView : BaseViews {
    fun showData(data: PersonalItems?)
    fun showTag(data:List<PersonalTags>?)
}