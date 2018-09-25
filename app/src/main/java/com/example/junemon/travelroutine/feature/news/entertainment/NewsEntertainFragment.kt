package com.example.junemon.travelroutine.feature.news.entertainment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.network.model.PersonalNewsEntertainment
import kotlinx.android.synthetic.main.activity_news_entertainment.*

class NewsEntertainFragment : Fragment(), NewsEntertainView {
    private var ctx: Context? = null
    var actualView: View? = null
    lateinit var presenter: NewsEntertainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter = NewsEntertainPresenter(this)
        presenter.onAttach(ctx)
        presenter.getLiveData(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = inflater.inflate(R.layout.activity_news_entertainment, container, false)
        presenter.onCreateView(views)
        return views
    }

    override fun getData(data: List<PersonalNewsEntertainment.Article>?) {
        rvNewsEntertain.layoutManager = LinearLayoutManager(ctx)
        rvNewsEntertain.adapter = NewsEntertainAdapter(ctx, data!!) {

        }
        rvNewsEntertain.adapter.notifyDataSetChanged()
    }

    override fun initView(view: View) {
        actualView = view
    }
}