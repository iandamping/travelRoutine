package com.example.junemon.travelroutine.feature.routine.output

import com.example.junemon.travelroutine.base.BaseFragmentsViews
import com.example.junemon.travelroutine.database.model.PersonalRoutines

interface OutputRoutineView : BaseFragmentsViews {
    fun showData(data: List<PersonalRoutines>?)
}