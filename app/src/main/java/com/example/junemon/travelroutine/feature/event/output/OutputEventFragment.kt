package com.example.junemon.travelroutine.feature.event.output

import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalEvent
import com.example.junemon.travelroutine.feature.event.input.InputEventActivity
import com.example.junemon.travelroutine.feature.event.input.InputEventDetail
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_output_event.*
import kotlinx.android.synthetic.main.activity_output_event.view.*
import kotlinx.android.synthetic.main.item_event_output.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

class OutputEventFragment : Fragment(), OutputEventView {
    private var ctx: Context? = null
    private var actualView: View? = null
    lateinit var presenter: OutputEventPresenter
    private var resId = R.anim.layout_animation_fall_down
    lateinit var animation: LayoutAnimationController
    lateinit var options: ActivityOptions

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter = OutputEventPresenter(this)
        presenter.onAttach(ctx)
        animation = AnimationUtils.loadLayoutAnimation(ctx, resId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getEventLiveData(this)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = inflater.inflate(R.layout.activity_output_event, container, false)
        presenter.onCreateView(views)
        return views
    }

    override fun showData(data: List<PersonalEvent>?) {
        if (data?.size != 0) {
            llEventEmptyData.visibility = View.GONE
        } else if (data.size == 0){
            llEventEmptyData.visibility = View.VISIBLE
        }

        actualView?.rvEvent?.layoutManager = LinearLayoutManager(ctx)
        actualView?.rvEvent?.adapter = OutputEventAdapter(ctx, data!!) {

 //            options = ActivityOptions.makeSceneTransitionAnimation(activity, *getPairedArray())
//            val intent = Intent(ctx, InputEventDetail::class.java)
//            intent.putExtra(InputEventActivity.INPUT_EVENT_ACTIVITY_KEY, it)
//            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//            startActivity(intent, options.toBundle())
            startActivity(intentFor<InputEventDetail>(InputEventActivity.INPUT_EVENT_ACTIVITY_KEY to it))

        }
        actualView?.rvEvent?.adapter?.notifyDataSetChanged()
    }

    override fun initView(view: View) {
        actualView = view
        actualView?.fabEvent?.setOnClickListener {
            startActivity<InputEventActivity>()
        }
    }

    fun getPairedArray(): Array<Pair<View, String>> {
        val pairImageView = Pair.create<View, String>(ivEventIcon, getString(R.string.image_testing_share))
        val pairTextViewEvent = Pair.create<View, String>(tvOutputEvent, getString(R.string.txt_event_testing_share))
        val pairTextViewDescr = Pair.create<View, String>(tvOutputEventDescription, getString(R.string.txt_event_testing_descr_share))
        val pairs = ArrayList<Pair<View, String>>()
        pairs.add(pairImageView)
        pairs.add(pairTextViewEvent)
        pairs.add(pairTextViewDescr)
        val pairArray: Array<Pair<View, String>> = pairs.toTypedArray()
        return pairArray
    }
}

