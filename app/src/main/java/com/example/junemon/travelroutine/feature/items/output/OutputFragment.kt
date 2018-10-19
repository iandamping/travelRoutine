package com.example.junemon.travelroutine.feature.items.output

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.feature.items.input.InputActivity
import com.example.junemon.travelroutine.feature.items.input.InputDetail
import kotlinx.android.synthetic.main.activity_output_items.*
import kotlinx.android.synthetic.main.activity_output_items.view.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity
import android.view.animation.AnimationUtils.loadLayoutAnimation
import android.view.animation.LayoutAnimationController



class OutputFragment : Fragment(), OutputView {


    private var ctx: Context? = null
    private var actualView: View? = null
    lateinit var presenter: OutputPresenter
    private var resId = R.anim.layout_animation_fall_down
    lateinit var animation:LayoutAnimationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter = OutputPresenter(this)
        presenter.onAttach(ctx)
        presenter.getAllLiveData(this)
        animation = AnimationUtils.loadLayoutAnimation(ctx, resId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = inflater.inflate(R.layout.activity_output_items, container, false)
        presenter.onCreateView(views)
        return views
    }

    override fun initView(view: View) {
        actualView = view
        actualView?.floating_action_button?.setOnClickListener {
            startActivity<InputActivity>()
        }
    }

    override fun showData(data: List<PersonalItems>?) {
        if (data?.size != 0) {
            llEmptyData.visibility = View.GONE
        } else if(data.size == 0){
            llEmptyData.visibility = View.VISIBLE
        }
        actualView?.rvInput?.layoutManager = LinearLayoutManager(ctx)
        actualView?.rvInput?.layoutAnimation = animation
        actualView?.rvInput?.adapter = OutputAdapter(ctx, data!!) {
            startActivity(intentFor<InputDetail>(InputActivity.INPUT_ITEMS_ACTIVITY_KEY to it))
        }
        actualView?.rvInput?.adapter?.notifyDataSetChanged()
    }


}
