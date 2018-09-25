package com.example.junemon.travelroutine.feature.news.bussines

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines
import kotlinx.android.synthetic.main.activity_news_bussines.*

class NewBussinesFragment : Fragment(), NewBussinesView {
    private var ctx: Context? = null
    var actualView: View? = null
    lateinit var presenter: NewBussinesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getLiveData(this)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter = NewBussinesPresenter(this)
        presenter.onAttach(ctx)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = inflater.inflate(R.layout.activity_news_bussines, container, false)
        presenter.onCreateView(views)
        return views
    }

    override fun initView(view: View) {
        actualView = view
    }

    override fun getData(data: List<PersonalNewsBussines.Article>?) {
        rvNewsBussiness.layoutManager = LinearLayoutManager(ctx)
        rvNewsBussiness.adapter = NewBussinesAdapter(ctx, data!!) {

        }
        rvNewsBussiness.adapter.notifyDataSetChanged()
    }


}