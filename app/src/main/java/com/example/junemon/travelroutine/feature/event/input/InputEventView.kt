package com.example.junemon.travelroutine.feature.event.input

import com.example.junemon.travelroutine.base.BaseViews
import com.example.junemon.travelroutine.database.model.PersonalEvent
import com.example.junemon.travelroutine.database.model.PersonalTags

interface InputEventView:BaseViews {
    fun showEventData(data: PersonalEvent?)
    fun showTag(data: List<PersonalTags>?)
}