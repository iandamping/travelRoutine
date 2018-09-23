package com.example.junemon.travelroutine.feature.output

import com.example.junemon.travelroutine.base.BaseFragmentsViews
import com.example.junemon.travelroutine.database.model.PersonalItems

interface OutputView : BaseFragmentsViews {
    fun showData(data: List<PersonalItems>?)
}