package com.example.junemon.travelroutine.feature.routine.output

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.base.BaseFragmentsPresenter
import com.example.junemon.travelroutine.repositories.Routine.viewmodel.LoadRoutineData

class OutputRoutinePresenter(var mView: OutputRoutineView) : BaseFragmentsPresenter {
    var ctx: Context? = null
    var viewModel: LoadRoutineData? = null
    override fun onAttach(context: Context?) {
        this.ctx = context
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun getAllLiveData(fragment: Fragment) {
        viewModel = ViewModelProviders.of(fragment).get(LoadRoutineData::class.java)
        viewModel?.loadMainData(MainApplication.MAIN_APPS)
        viewModel?.getPersonalLiveData()?.observe(fragment, Observer { results -> mView.showData(results) })
    }
}