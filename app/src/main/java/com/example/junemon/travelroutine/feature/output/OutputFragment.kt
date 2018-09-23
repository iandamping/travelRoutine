package com.example.junemon.travelroutine.feature.output

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.feature.input.InputActivity
import com.example.junemon.travelroutine.feature.input.InputDetail
import kotlinx.android.synthetic.main.activity_output.view.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity

class OutputFragment : Fragment(), OutputView {


    private var ctx: Context? = null
    private var actualView: View? = null
    lateinit var presenter: OutputPresenter
    lateinit var data: List<PersonalItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter = OutputPresenter(this)
        presenter.onAttach(ctx)
        presenter.getAllLiveData(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = inflater.inflate(R.layout.activity_output, container, false)
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
        actualView?.rvInput?.layoutManager = LinearLayoutManager(ctx)
        actualView?.rvInput?.adapter = OutputAdapter(ctx, data!!) {
            startActivity(intentFor<InputDetail>(InputActivity.INPUT_ACTIVITY_KEY to it))
        }
        actualView?.rvInput?.adapter?.notifyDataSetChanged()
    }

}
