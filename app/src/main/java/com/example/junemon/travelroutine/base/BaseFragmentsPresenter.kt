package com.example.junemon.travelroutine.base

import android.content.Context
import android.view.View

interface BaseFragmentsPresenter {
    fun onAttach(context: Context?)
    fun onCreateView(view: View)

}