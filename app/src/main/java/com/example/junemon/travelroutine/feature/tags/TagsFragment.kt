package com.example.junemon.travelroutine.feature.tags

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.feature.tags.inputTags.InputTagActivity
import com.example.junemon.travelroutine.repositories.Tags.TagsRepositories
import kotlinx.android.synthetic.main.activity_adding_tag.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity


class TagsFragment : Fragment(), TagsView {
    private var ctx: Context? = null
    private var actualView: View? = null
    private var presenter: TagsPresenter = TagsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter.onAttach(ctx)
        presenter.getLiveDataAllTag(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = LayoutInflater.from(ctx).inflate(R.layout.activity_adding_tag, container, false)
        presenter.onCreateView(views)
        return views

    }

    override fun initView(view: View) {
        actualView = view
        actualView?.setOnClickListener { startActivity<InputTagActivity>() }
    }

    override fun getData(data: List<PersonalTags>?) {
        rvNewTag.layoutManager = LinearLayoutManager(ctx)
        rvNewTag.adapter = TagsAdapter(ctx, data!!) { it ->
            startActivity(intentFor<InputTagActivity>("Keys" to it).singleTop())
        }
    }


    fun initiateFirst(ctx: Context?) {
        val prefs = ctx?.getSharedPreferences(TagsRepositories.DEVICE_TOKEN, MODE_PRIVATE)
        val pageNumber = prefs?.getInt(TagsRepositories.KEY, 0)

        if (pageNumber == 0) {
            TagsRepositories.initFirstItemTag(ctx)
        }

    }
}


