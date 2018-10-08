package com.example.junemon.travelroutine.feature.routine.input

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import com.example.junemon.travelroutine.base.BasePresenters
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.repositories.Routine.RoutineRepositories
import com.example.junemon.travelroutine.repositories.Tags.viewmodel.GetPersonalTagRepo

class InputRoutinePresenter(var mView: InputRoutineView) : BasePresenters {
    private lateinit var ctx: Context
    private var viewModel: GetPersonalTagRepo? = null
    private lateinit var repo: RoutineRepositories
    override fun getContext(): Context? {
        return ctx
    }

    override fun onCreate(context: Context) {
        this.ctx = context
        mView.initView()
        mView.initListener()
        repo = RoutineRepositories()
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

    override fun onPause() {
    }

    fun getData(data: PersonalRoutines?) {
        mView.showData(data)
    }

    fun insertData(data: PersonalRoutines?) {
        repo.insertData(data)
    }

    fun deleteData(data: PersonalRoutines?) {
        repo.deleteData(data)
    }

    fun getLiveDataAllTag(frag: FragmentActivity) {
        viewModel = ViewModelProviders.of(frag).get(GetPersonalTagRepo::class.java)
        viewModel?.getData()
        viewModel?.getPersonalTagLiveData()?.observe(frag, Observer { results ->
            mView.showTag(results)
        })
    }

    fun finishObserving() {
        repo.finishObserving()
    }
}