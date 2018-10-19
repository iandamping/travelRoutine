package com.example.junemon.travelroutine.feature.event.output

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import com.example.junemon.travelroutine.base.BaseFragmentsPresenter
import com.example.junemon.travelroutine.repositories.Event.viewmodel.GetPersonalEventRepo

class OutputEventPresenter(var mView: OutputEventView) : BaseFragmentsPresenter {
    private var ctx: Context? = null
    var viewmodel: GetPersonalEventRepo? = null
    override fun onAttach(context: Context?) {
        this.ctx = context
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun getEventLiveData(target: Fragment) {
        viewmodel = ViewModelProviders.of(target).get(GetPersonalEventRepo::class.java)
        viewmodel?.LoadEventData()?.observe(target, Observer { results ->
            mView.showData(results)
        })
    }
}