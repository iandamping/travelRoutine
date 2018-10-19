package com.example.junemon.travelroutine.feature.event.input

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import com.example.junemon.travelroutine.base.BasePresenters
import com.example.junemon.travelroutine.database.model.PersonalEvent
import com.example.junemon.travelroutine.repositories.Event.EventRepositories
import com.example.junemon.travelroutine.repositories.Tags.viewmodel.GetPersonalTagRepo

class InputEventPresenter(var mView: InputEventView) : BasePresenters {
    private lateinit var ctx: Context
    private lateinit var repos: EventRepositories
    private var viewModel: GetPersonalTagRepo? = null

    override fun getContext(): Context? {
        return ctx
    }

    override fun onCreate(context: Context) {
        this.ctx = context
        mView.initView()
        mView.initListener()
        repos = EventRepositories()
    }

    fun formatedDate(years: Int, months: Int, dayOfMonth: Int): String {
        val month: Int = months + 1
        var formatMonth = "" + month
        var formatDay = "" + dayOfMonth
        if (month < 10) {
            formatMonth = "0" + month
        }
        if (dayOfMonth < 10) {
            formatDay = "0" + dayOfMonth
        }
        return formatDay + "-" + formatMonth + "-" + years.toString()
    }


    fun saveEventData(data: PersonalEvent?) {
        repos.insertData(data)
    }

    fun getDataDetail(data: PersonalEvent?) {
        mView.showEventData(data)
    }

    fun deleteData(data: PersonalEvent?) {
        repos.deleteData(data)
    }

    fun getLiveDataAllTag(frag: FragmentActivity) {
        viewModel = ViewModelProviders.of(frag).get(GetPersonalTagRepo::class.java)
        viewModel?.getData()
        viewModel?.getPersonalTagLiveData()?.observe(frag, Observer { results ->
            mView.showTag(results)
        })
    }

    fun finishObserving() {
        repos.finishObserving()
    }

    override fun onPause() {
    }
}