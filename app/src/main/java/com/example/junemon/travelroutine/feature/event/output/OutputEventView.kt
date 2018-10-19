package com.example.junemon.travelroutine.feature.event.output

import com.example.junemon.travelroutine.base.BaseFragmentsViews
import com.example.junemon.travelroutine.database.model.PersonalEvent

interface OutputEventView : BaseFragmentsViews {
    fun showData(data: List<PersonalEvent>?)
}