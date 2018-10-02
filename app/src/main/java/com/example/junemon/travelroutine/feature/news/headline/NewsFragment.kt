package com.example.junemon.travelroutine.feature.news.headline

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.MainApplication.Companion.builder
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.helper.customtabhelper.CustomTabActivityHelper
import com.example.junemon.travelroutine.network.model.PersonalNews
import kotlinx.android.synthetic.main.activity_news_data.*


class NewsFragment : Fragment(), NewsFragmentView {
    private var ctx: Context? = null
    var actualView: View? = null
    lateinit var presenter: NewsFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter = NewsFragmentPresenter(this)
        presenter.onAttach(ctx)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getLiveData(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = inflater.inflate(R.layout.activity_news_data, container, false)
        presenter.onCreateView(views)
        return views
    }

    override fun newsHeadline(data: List<PersonalNews.Article>?) {
        rvNewsData.layoutManager = LinearLayoutManager(ctx)
        rvNewsData.adapter = NewsFragmentAdapter(ctx, data!!) {
            //            builder.launchUrl(ctx, Uri.parse(it.url))
            CustomTabActivityHelper.openCustomTab(activity!!, builder, Uri.parse(it.url),
                    object : CustomTabActivityHelper.CustomTabFallback {
                        override fun openUri(activity: Activity, uri: Uri) {
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            activity.startActivity(intent)
                        }
                    })
        }

    }

    override fun initView(view: View) {
        actualView = view
    }


}