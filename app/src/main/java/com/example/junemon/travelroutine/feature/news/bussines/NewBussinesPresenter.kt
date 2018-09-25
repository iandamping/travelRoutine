package com.example.junemon.travelroutine.feature.news.bussines

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.base.BaseFragmentsPresenter
import com.example.junemon.travelroutine.repositories.News.viewmodel.GetBussinesRepo

class NewBussinesPresenter(var mView: NewBussinesView) : BaseFragmentsPresenter {
    private var ctx: Context? = null
    var viewModel: GetBussinesRepo? = null

    override fun onAttach(context: Context?) {
        this.ctx = context
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun getLiveData(fragment: Fragment) {
        viewModel = ViewModelProviders.of(fragment).get(GetBussinesRepo::class.java)
        viewModel?.getData(MainApplication.MAIN_APPS)
        viewModel?.getPersonalNewsLiveData()?.observe(fragment, Observer { results ->
            mView.getData(results)
        })
    }
}