package com.example.junemon.travelroutine.feature.input

import com.example.junemon.travelroutine.base.BaseViews
import com.example.junemon.travelroutine.database.model.PersonalItems

interface InputView : BaseViews {
    fun showData(data: PersonalItems?)

}