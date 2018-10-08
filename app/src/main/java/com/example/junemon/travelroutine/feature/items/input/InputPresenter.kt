package com.example.junemon.travelroutine.feature.items.input

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import com.example.junemon.travelroutine.base.BasePresenters
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.repositories.Items.ItemRepositories
import com.example.junemon.travelroutine.repositories.Tags.viewmodel.GetPersonalTagRepo

class InputPresenter(var mView: InputView) : BasePresenters {
    private lateinit var ctx: Context
    private lateinit var repos: ItemRepositories
    private var viewModel: GetPersonalTagRepo? = null
    override fun getContext(): Context? {
        return ctx
    }

    override fun onCreate(context: Context) {
        ctx = context
        mView.initView()
        mView.initListener()
        repos = ItemRepositories()
    }

    override fun onPause() {
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

    fun getData(data: PersonalItems?) {
        mView.showData(data)
    }


    fun insertData(data: PersonalItems?) {
        repos.insertData(data)
    }

    fun updateData(data: PersonalItems?) {
        repos.updateData(data)
    }

    fun deleteData(data: PersonalItems?) {
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

}