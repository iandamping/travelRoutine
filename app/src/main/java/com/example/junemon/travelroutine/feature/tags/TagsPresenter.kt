package com.example.junemon.travelroutine.feature.tags

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import com.example.junemon.travelroutine.base.BaseFragmentsPresenter
import com.example.junemon.travelroutine.repositories.Tags.viewmodel.GetPersonalTagRepo


class TagsPresenter(var mView: TagsView) : BaseFragmentsPresenter {
    var ctx: Context? = null

    private var viewModel: GetPersonalTagRepo? = null
    
    override fun onAttach(context: Context?) {
        this.ctx = context
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun getLiveDataAllTag(frag: Fragment) {
        viewModel = ViewModelProviders.of(frag).get(GetPersonalTagRepo::class.java)
        viewModel?.getData()
        viewModel?.getPersonalTagLiveData()?.observe(frag, Observer { results ->
            mView.getData(results)
        })
    }


}