package com.example.junemon.travelroutine.feature.routine.output

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.feature.routine.input.InputRoutineActivity
import com.example.junemon.travelroutine.feature.routine.input.InputRoutineDetail
import kotlinx.android.synthetic.main.activity_output_routines.*
import kotlinx.android.synthetic.main.activity_output_routines.view.*
import org.jetbrains.anko.support.v4.intentFor

class OutputRoutineFragment : Fragment(), OutputRoutineView {
    private var ctx: Context? = null
    lateinit var presenter: OutputRoutinePresenter
    lateinit var data: List<PersonalRoutines>
    private var resId = R.anim.layout_animation_fall_down
    lateinit var animation: LayoutAnimationController
    private var actualView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter = OutputRoutinePresenter(this)
        presenter.onAttach(ctx)
        presenter.getAllLiveData(this)
        animation = AnimationUtils.loadLayoutAnimation(ctx, resId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var views: View = LayoutInflater.from(ctx).inflate(R.layout.activity_output_routines, container, false)
        presenter.onCreateView(views)
        return views
    }

    override fun showData(data: List<PersonalRoutines>?) {
        if (data?.size != 0) {
            llEmptyRoutine.visibility = View.GONE
        } else if (data.size == 0) {
            llEmptyRoutine.visibility = View.VISIBLE
        }
        actualView?.rvInputRoutine?.layoutManager = LinearLayoutManager(ctx)
        actualView?.rvInputRoutine?.layoutAnimation = animation
        actualView?.rvInputRoutine?.adapter = OutputRoutineAdapter(ctx, data!!) {

            startActivity(intentFor<InputRoutineDetail>(InputRoutineActivity.INPUT_ROUTINE_ITEM_KEYS to it))
        }

        actualView?.rvInputRoutine?.adapter?.notifyDataSetChanged()
    }

    override fun initView(view: View) {
        actualView = view
        actualView?.fabOutputRoutine?.setOnClickListener {
            startActivity(intentFor<InputRoutineActivity>())
        }
    }
}