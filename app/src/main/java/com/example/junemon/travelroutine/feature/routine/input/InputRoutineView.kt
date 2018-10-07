package com.example.junemon.travelroutine.feature.routine.input

import com.example.junemon.travelroutine.base.BaseViews
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.database.model.PersonalTags

interface InputRoutineView : BaseViews {
    fun showData(data: PersonalRoutines?)
    fun showTag(data: List<PersonalTags>?)
}